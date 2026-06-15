# QuarkusExtension

This is a sample that demonstrates how to write and deploy a new quarkus extension using gradle and kotlin 

This extension provides a restclient that sends an instance of MyBody, to the provided URI, using a basic authentication

```kotlin
class MyBody (
    val id: String,
    val content: String
)
```

All parameters are configured in the application.properties under the prefix my-extension as you can see below.

```kotlin
@ConfigMapping(prefix = "my-extension")
interface MyConfig {
    fun uri(): URI
    fun username(): String
    fun password(): String
}
```

