import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "17"
        }
    }
    sourceSets {
        val jvmMain by getting  {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(project(":core"))
                implementation(project(":ui"))
                implementation(project(":di"))
                implementation(project(":localization"))
            }
        }
        val jvmTest by getting
        val commonMain by getting  {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(project(":core"))
                implementation(project(":ui"))
                implementation(project(":di"))
                implementation(project(":localization"))
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Logistics"
            packageVersion = "1.0.0"
        }
    }
}

