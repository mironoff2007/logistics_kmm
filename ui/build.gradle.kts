repositories {
    google()
    mavenCentral()
}

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.kotlin.jvm") apply false
    id("org.jetbrains.compose")
    kotlin("plugin.serialization")
}

kotlin {
    jvm("desktop")
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {
            val serializationVersion: String by project
            dependencies {
                implementation (project(":core"))
                implementation (project(":domain"))
                implementation (project(":repo"))
                implementation (project(":server_contract"))
                implementation (project(":localization"))
                implementation (project(":logging"))
                implementation (project(":database"))
                implementation (project(":sharedprefs"))

                api("me.tatarka.inject:kotlin-inject-runtime:0.6.1")

                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)


                implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.5")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")

                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")

                implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.20")

            }
        }
    }
}



