rootProject.name = "Logistics"

include(":androidApp")
include(":shared")
include(":core")
include(":desktop")
include(":domain")
include(":database")
include(":repo")
include(":sharedprefs")
include(":logging")
include(":di")
include(":ui")
include(":localization")
include(":server_contract")


pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
    }

    plugins {
        val kotlinVersion = extra["kotlin.version"] as String
        val agpVersion = extra["agp.version"] as String
        val composeVersion = extra["compose.version"] as String
        val kspVersion = extra["kspVersion"] as String

        id("com.google.devtools.ksp").version(kspVersion)
        kotlin("jvm").version(kotlinVersion)
        kotlin("multiplatform").version(kotlinVersion)
        kotlin("android").version(kotlinVersion)

        id("com.android.application").version(agpVersion)
        id("com.android.library").version(agpVersion)

        id("org.jetbrains.compose").version(composeVersion)

        kotlin("plugin.serialization").version(extra["kotlin.version"] as String)

        id("app.cash.sqldelight").version(extra["sqldelightVersion"] as String)
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}
