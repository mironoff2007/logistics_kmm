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
        val okioVersion = "3.3.0"
        val commonMain by getting {
            dependencies {
                implementation (project(":core"))
                implementation (project(":domain"))

                api("me.tatarka.inject:kotlin-inject-runtime:0.6.1")

                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")

                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")

                implementation("com.squareup.okio:okio:$okioVersion")

            }
        }
    }
}
