package com.pconil.quarkus.extensions.my_extension

import io.quarkus.rest.client.reactive.Url
import io.smallrye.mutiny.Uni
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.HeaderParam
import jakarta.ws.rs.POST
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType.APPLICATION_JSON
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient
import java.net.URI

@RegisterRestClient(baseUri = "/")
interface MyClient {
    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    fun post(@Url uri: URI,
             @HeaderParam("Authorization") authorization: String?,
             body: MyBody
    ): Uni<Response>
}