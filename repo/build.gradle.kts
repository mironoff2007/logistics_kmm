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
                implementation (project(":database"))
                implementation (project(":core"))
                implementation (project(":domain"))
                implementation (project(":logging"))
                implementation (project(":server_contract"))

                api("me.tatarka.inject:kotlin-inject-runtime:0.6.3")

                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")

                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
            }
        }
    }
}
