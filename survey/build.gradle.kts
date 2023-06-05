@file:Suppress("SuspiciousCollectionReassignment")

plugins {
    id("com.android.library")
    kotlin("android")
    id("gradle-publishing")
    id("kotlin-parcelize")
}

android {
    compileSdkVersion = Project.Android.compileSdkVersion

    defaultConfig {
        version = Library.version
        minSdkPreview = Project.Android.minSdkVersion
        targetSdkPreview = Project.Android.targetSdkVersion
        testInstrumentationRunner = Project.Android.testInstrumentationRunner
    }

    buildFeatures {
        viewBinding = true
    }

    testOptions {
        animationsDisabled = true
    }
    namespace = "com.quickbirdstudios.surveykit"
}

dependencies {
    implementation(Deps.Kotlin.stdlib)
    api(Deps.Kotlin.coroutines)
    implementation(Deps.Kotlin.androidCoroutines)
    implementation(Deps.AndroidSupport.appCompat)
    implementation(Deps.AndroidSupport.constraint)
    implementation(Deps.AndroidSupport.recyclerView)
    implementation(Deps.lottie)
    implementation(Deps.PlayServices.maps)
    implementation(Deps.Google.material)

    testImplementation(Deps.Test.jUnitJupiter)
    testImplementation(Deps.Test.jUnitPlatform)
}