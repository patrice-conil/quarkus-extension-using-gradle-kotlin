package com.pconil

import jakarta.enterprise.inject.Produces
import jakarta.enterprise.inject.spi.InjectionPoint
import io.vertx.core.impl.logging.Logger
import io.vertx.core.impl.logging.LoggerFactory

class LoggerProducer {
    @Produces
    fun produceLogger(injectionPoint: InjectionPoint): Logger {
        val className = injectionPoint.member.declaringClass.name
        return LoggerFactory.getLogger(className)
    }
}
