import org.jetbrains.compose.ExperimentalComposeLibrary

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.compose)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {

        commonMain.dependencies {
            // compose
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            // ktor
            implementation(libs.bundles.ktor)
            // kotlinx datetime
            implementation(libs.kotlinx.datetime)
            // kotlinx serialization(json)
            implementation(libs.kotlinx.serialization.json)
            // voyager
            implementation(libs.bundles.voyager)
            // koin
            implementation(libs.koin.core)
            // kamel
            implementation(libs.kamel.image)
        }

        commonTest.dependencies {
            // kotlin test
            implementation(libs.kotlin.test)
            // ktor
            implementation(libs.ktor.client.mock)
            // kotlin - coroutines
            implementation(libs.kotlinx.coroutines.test)
            // koin
            implementation(libs.koin.test)
        }

        androidMain.dependencies {
            // compose
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
            // ktor
            implementation(libs.ktor.client.okhttp)
            implementation(libs.compose.ui.test.junit4)
        }

        iosMain.dependencies {
            // ktor
            implementation(libs.ktor.client.darwin)
        }
    }
}

android {
    namespace = "com.mambo.app.weather"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.mambo.app.weather"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
        debugImplementation(libs.compose.ui.test.manifest)
    }
}

