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
                implementation(project(":domain"))
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")

            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}
