plugins {
    kotlin("multiplatform")
    id("com.android.application")
    id("org.jetbrains.compose")
    kotlin("plugin.serialization")
}

kotlin {
    androidTarget()

    val serializationVersion: String by project
    dependencies {
        implementation(project(":core"))
        implementation(project(":ui"))
        implementation(project(":di"))
        implementation(project(":domain"))
        implementation(project(":localization"))
        implementation(project(":database"))
        implementation(project(":sharedprefs"))
        implementation(project(":logging"))

        implementation("androidx.activity:activity-compose:1.5.0")
        implementation("com.google.accompanist:accompanist-systemuicontroller:0.17.0")
        implementation("com.google.accompanist:accompanist-permissions:0.25.1")

        implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.5")
        implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")

        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")


        //Testing
        implementation("junit:junit:4.13.2")
        implementation("androidx.test.ext:junit:1.1.5")
        implementation("androidx.test.espresso:espresso-core:3.5.1")
        // Test rules and transitive dependencies:
        // Needed for createAndroidComposeRule, but not createComposeRule:
        implementation("androidx.compose.ui:ui-test-junit4:1.2.0")
        implementation("androidx.compose.ui:ui-test-manifest:1.4.3")

        implementation("androidx.core:core-ktx:1.8.0")
        implementation("androidx.compose.ui:ui-tooling-preview:1.4.3")
        implementation("androidx.compose.ui:ui-tooling:1.4.3")
        implementation("androidx.test:rules:1.5.0")
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

        testInstrumentationRunner = "ru.mironov.logistics.CustomTestRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
}
