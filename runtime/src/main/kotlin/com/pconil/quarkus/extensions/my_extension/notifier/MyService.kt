package com.pconil.quarkus.extensions.my_extension.notifier


import com.pconil.quarkus.extensions.my_extension.MyBody
import com.pconil.quarkus.extensions.my_extension.MyClient
import com.pconil.quarkus.extensions.my_extension.MyConfig
import io.smallrye.mutiny.Uni
import io.vertx.core.impl.logging.LoggerFactory
import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.InternalServerErrorException
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.rest.client.inject.RestClient
import java.util.*

@ApplicationScoped
class MyService(
    @RestClient private val client: MyClient,
    private val config: MyConfig
) {
    private val logger = LoggerFactory.getLogger(this.javaClass.canonicalName)

    private fun <T> handleFailure(failure: Throwable): Uni<T> {
        logger.error(failure.message)
        return Uni.createFrom().failure(InternalServerErrorException(failure.message))
    }

    fun post(body: MyBody): Uni<Response> {
        val auth = if (config.username().isNullOrBlank() || config.password().isNullOrBlank()) {
            null
        } else {
            val base64 = Base64.getEncoder().encodeToString(
                "${config.username()}:${config.password()}".toByteArray(Charsets.UTF_8)
            )
            "Basic $base64"
        }
        return client.post(config.uri(), auth, body)
            .map { response ->
                logger.trace("Successfully posted: ${response.status}")
                response
            }
            .onFailure()
            .recoverWithUni { failure -> handleFailure(failure) }
    }
}
