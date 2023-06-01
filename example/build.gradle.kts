import com.quickbirdstudios.surveykit.ApiKeys.googleMapsKey
import com.quickbirdstudios.surveykit.ApiKeys.yandexMapsKey

plugins {
    id("com.android.application")
    kotlin("android")
    id("org.jetbrains.kotlin.android.extensions")
}

androidExtensions { isExperimental = true }

android {
    compileSdkVersion = Project.Android.compileSdkVersion

    defaultConfig {
        versionName = Library.version
        versionCode = 1
        minSdkPreview = Project.Android.minSdkVersion
        targetSdkVersion(Project.Android.targetSdkVersion)
        testInstrumentationRunner = Project.Android.testInstrumentationRunner
        resValue("string", "google_api_key", googleMapsKey())
        resValue("string", "yandex_api_key", yandexMapsKey())
    }
    packagingOptions {
        jniLibs {
            excludes += setOf("META-INF/*kotlin*")
        }
        resources {
            excludes += setOf("META-INF/*kotlin*")
        }
    }
    namespace = "com.quickbirdstudios.example"
}

dependencies {
    implementation(project(":survey"))

    /* Kotlin */
    implementation(Deps.Kotlin.stdlib)
    implementation(Deps.Kotlin.reflect)
    implementation(Deps.Kotlin.coroutines)

    /* Android Support */
    implementation(Deps.AndroidSupport.appCompat)
    implementation(Deps.AndroidSupport.constraint)
}
