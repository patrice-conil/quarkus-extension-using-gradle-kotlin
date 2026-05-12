package com.pconil.quarkus.extensions.my_extension

import io.smallrye.config.ConfigMapping
import java.net.URI

@ConfigMapping(prefix = "my-extension")
interface MyConfig {
    fun uri(): URI
    fun username(): String?
    fun password(): String?
}