@file:Suppress("SuspiciousCollectionReassignment")

plugins {
    id("com.android.library")
    kotlin("android")
    id("org.jetbrains.kotlin.android.extensions")
    id("gradle-publishing")
}

androidExtensions { isExperimental = true }

android {
    compileSdkVersion(Project.Android.compileSdkVersion)

    defaultConfig {
        version = Library.version
        minSdkVersion(Project.Android.minSdkVersion)
        targetSdkVersion(Project.Android.targetSdkVersion)
        testInstrumentationRunner = Project.Android.testInstrumentationRunner
    }

    testOptions {
        animationsDisabled = true
    }
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