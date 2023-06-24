plugins {
    kotlin("multiplatform")
    id("com.android.application")
    id("org.jetbrains.compose")
}

kotlin {
    android()
    sourceSets {
        val androidMain by getting {
            dependencies {
                implementation(project(":core"))
                implementation(project(":ui"))
                implementation(project(":di"))
                implementation(project(":localization"))
                implementation(project(":database"))
                implementation(project(":logging"))

                implementation ("androidx.activity:activity-compose:1.5.0")
                implementation ("com.google.accompanist:accompanist-systemuicontroller:0.17.0")
                implementation ("com.google.accompanist:accompanist-permissions:0.25.1")

                //Testing
                implementation ("junit:junit:4.13.2")
                implementation ("androidx.test.ext:junit:1.1.5")
                implementation ("androidx.test.espresso:espresso-core:3.5.1")
                // Test rules and transitive dependencies:
                // Needed for createAndroidComposeRule, but not createComposeRule:
                implementation("androidx.compose.ui:ui-test-manifest:1.4.3")

                implementation ("androidx.core:core-ktx:1.8.0")
                implementation("androidx.compose.ui:ui-tooling-preview:1.4.3")
                implementation("androidx.compose.ui:ui-tooling:1.4.3")

                implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.5")
            }
        }
    }
}

android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    namespace = "com.logistics"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    defaultConfig {
        applicationId = "com.logistics.Logistics"
        minSdk = (findProperty("android.minSdk") as String).toInt()
        targetSdk = (findProperty("android.targetSdk") as String).toInt()
        versionCode = 1
        versionName = "1.0"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        jvmToolchain(11)
    }
}
