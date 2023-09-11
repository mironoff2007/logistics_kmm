plugins {
    kotlin("multiplatform")
    id("org.jetbrains.kotlin.jvm") apply false
    kotlin("plugin.serialization")
    id("com.google.devtools.ksp")
}

kotlin {
    jvm("desktop")
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":ui"))
                implementation(project(":core"))
                implementation(project(":domain"))
                implementation(project(":logging"))
                implementation(project(":sharedprefs"))
                implementation(project(":server_contract"))
                implementation(project(":repo"))
                implementation(project(":database"))

                runtimeOnly("org.jetbrains.kotlin:kotlin-reflect:${findProperty("kotlin.version")}")

                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.0")
                implementation("me.tatarka.inject:kotlin-inject-runtime:0.6.1")

            }
        }
    }

    dependencies {
        //KInject
        ksp("me.tatarka.inject:kotlin-inject-compiler-ksp:0.6.3")
    }
}
