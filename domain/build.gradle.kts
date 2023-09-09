plugins {
    kotlin("multiplatform")
    id("org.jetbrains.kotlin.jvm") apply false
    kotlin("plugin.serialization")
}

kotlin {
    jvm("desktop")
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    val serializationVersion: String by project
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":localization"))
                implementation(project(":server_contract"))

                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")

                api("me.tatarka.inject:kotlin-inject-runtime:0.6.1")

                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}
