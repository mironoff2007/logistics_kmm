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

        }
    }
}