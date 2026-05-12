package com.pconil

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.ok
import com.github.tomakehurst.wiremock.client.WireMock.post
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import com.github.tomakehurst.wiremock.common.ConsoleNotifier
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import jakarta.ws.rs.core.Response
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import jakarta.inject.Inject

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ControllerTest {
    @Inject
    lateinit var objectMapper: ObjectMapper

    val wiremock: WireMockServer = WireMockServer(
        WireMockConfiguration.options().port(8888).notifier(ConsoleNotifier(true))
    )

    @BeforeEach
    fun startUp() {
        wiremock.start()
    }

    @AfterEach
    fun tearDown() {
        wiremock.stop()
    }

    @Test
    fun testHelloEndpoint() {
        class MyResponse(val status: Response.Status, val message: String)
        val expected = objectMapper.writeValueAsString(
            MyResponse(Response.Status.OK, "All is fine")
        )
        wiremock.stubFor(
            post(urlEqualTo("/"))
                .willReturn(ok(expected))
        )

        given()
            .`when`().get("/send")
            .then()
            .statusCode(200)
            .body(`is`(expected))
    }
}
