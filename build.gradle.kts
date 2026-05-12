plugins {
    idea
    kotlin("jvm")
    id("java-library")
    id("maven-publish")
}

val group: String by project
val version: String by project

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
    withSourcesJar()
}

// Configure subprojects
subprojects {
    afterEvaluate {
        java {
            sourceCompatibility = JavaVersion.VERSION_21
            targetCompatibility = JavaVersion.VERSION_21
        }

        kotlin {
            compilerOptions {
                jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21
                javaParameters = true
                freeCompilerArgs.set(listOf("-Xjvm-default=all", "-Xannotation-default-target=param-property"))
                apiVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_0)
            }
        }

        tasks.withType<GenerateModuleMetadata>().configureEach {
            suppressedValidationErrors.add("enforced-platform")
        }

        repositories {
            mavenLocal()
            mavenCentral()
        }

        if (name in listOf("runtime", "deployment")) {
            dependencies {
                implementation(rootProject)
            }
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = group
            artifactId = "my-extension-parent"
            from(components["kotlin"])

            pom {
                // Add module section into parent pom
                withXml {
                    val modulesNode = asNode().appendNode("modules")
                    listOf("runtime", "deployment").forEach { moduleName ->
                        modulesNode.appendNode("module", moduleName)
                    }
                }
            }
        }
    }
}
