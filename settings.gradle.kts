pluginManagement {
    val quarkusPlatformArtifactId: String by settings
    val quarkusVersion: String by settings

    val openApiGeneratorVersion: String by settings
    val dependencyCheckVersion: String by settings
    val detektVersion: String by settings
    val dependencyLicenseReportVersion: String by settings
    val sonarqubeVersion: String by settings
    val dependencyUpdateVersion: String by settings

    fun getKotlinVersion(): String {
        val propertyResource =
            java.net.URI(
                "${repositories.mavenCentral().url}io/quarkus/" +
                        "${quarkusPlatformArtifactId}/${quarkusVersion}" +
                        "/${quarkusPlatformArtifactId}-${quarkusVersion}.pom"
            ).toURL()

        val dbf = javax.xml.parsers.DocumentBuilderFactory.newInstance()
        val bom = dbf.newDocumentBuilder().parse(propertyResource.openStream())

        val xPathfactory = javax.xml.xpath.XPathFactory.newInstance()
        val xpath = xPathfactory.newXPath()

        val depXPath =
            xpath.compile("/project/dependencyManagement/dependencies/dependency[artifactId='kotlin-compiler']")
        val depend = depXPath.evaluate(bom, javax.xml.xpath.XPathConstants.NODESET) as org.w3c.dom.NodeList
        return xpath.evaluate("version", depend.item(0))
    }

    val kotlinVersion = getKotlinVersion()

    println("Gradle Version ${gradle.gradleVersion}")
    println("Kotlin Version $kotlinVersion")

    repositories {
        mavenLocal()
        gradlePluginPortal()
        mavenCentral()
    }
    plugins {
        id("io.quarkus") version quarkusVersion
        id("io.quarkus.extension") version quarkusVersion
        kotlin("jvm") version kotlinVersion
        kotlin("kapt") version kotlinVersion
        kotlin("plugin.allopen") version kotlinVersion
        id("org.openapi.generator") version openApiGeneratorVersion
        id("io.gitlab.arturbosch.detekt") version detektVersion
        id("org.owasp.dependencycheck") version dependencyCheckVersion
        id("com.github.jk1.dependency-license-report") version dependencyLicenseReportVersion
        id("org.sonarqube") version sonarqubeVersion
        id("com.github.ben-manes.versions") version dependencyUpdateVersion
    }
}

rootProject.name = "my-extension"
include("runtime", "deployment", "integration-tests")
