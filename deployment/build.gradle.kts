plugins {
    idea
    java
    id("java-library")
    id("maven-publish")
    kotlin("jvm")
    kotlin("plugin.allopen")
    kotlin("kapt")
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusVersion: String by project
val version: String by project
val group: String by project

dependencies {
    implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusVersion}"))
    implementation(project(":runtime"))
    implementation("io.quarkus:quarkus-arc-deployment")
    implementation("io.quarkus:quarkus-rest-deployment")
    implementation("io.quarkus:quarkus-kotlin-deployment")
    implementation("io.quarkus:quarkus-scheduler-deployment")
    implementation("io.quarkus:quarkus-rest-client-jackson-deployment")
    implementation("io.quarkus:quarkus-hibernate-validator-deployment")
    implementation("io.quarkus:quarkus-smallrye-jwt-build-deployment")
    kapt("io.quarkus:quarkus-extension-processor:3.33.1")
}

tasks.withType<GenerateModuleMetadata>().configureEach {
    suppressedValidationErrors.add("enforced-platform")
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = group
            artifactId = "my-extension-deployment"
            this.version = version
            from(components["kotlin"])
            artifact(sourcesJar.get())
        }
    }
}
