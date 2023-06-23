plugins {
    kotlin("multiplatform")
    id("org.jetbrains.kotlin.jvm") apply false
}

kotlin {
    jvm("desktop")
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation (project(":shared"))
                implementation (project(":domain"))

                api("me.tatarka.inject:kotlin-inject-runtime:0.6.1")

                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")

            }
        }
    }
}
