plugins {
    kotlin("multiplatform")
    id("org.jetbrains.kotlin.jvm") apply false
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
                implementation(project(":core"))
                implementation(project(":domain"))
                implementation(project(":logging"))

                api("me.tatarka.inject:kotlin-inject-runtime:0.6.1")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")

            }
        }
    }
}

