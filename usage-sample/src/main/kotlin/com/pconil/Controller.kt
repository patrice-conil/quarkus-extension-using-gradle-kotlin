package com.pconil

import com.pconil.quarkus.extensions.my_extension.MyBody
import com.pconil.quarkus.extensions.my_extension.notifier.MyService
import io.smallrye.mutiny.Uni
import io.vertx.core.impl.logging.Logger
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("/")
class Controller(
    val service: MyService,
    val logger: Logger
) {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/send")
    fun send(): Uni<Response> {
        logger.debug("Will send a request using MyService")
        return service.post(MyBody("1", "This is the content"))
    }
}
