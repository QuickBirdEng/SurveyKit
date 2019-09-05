@file:Suppress("SuspiciousCollectionReassignment")

import com.quickbirdstudios.surveykit.configureLibraryPublication

plugins {
    id("com.android.library")
    kotlin("android")
    id("org.jetbrains.kotlin.android.extensions")
    id("com.jfrog.bintray")
    `maven-publish`
}

androidExtensions { isExperimental = true }

android {
    compileSdkVersion(Project.Android.compileSdkVersion)

    defaultConfig {
        minSdkVersion(Project.Android.minSdkVersion)
        targetSdkVersion(Project.Android.targetSdkVersion)
        testInstrumentationRunner = Project.Android.testInstrumentationRunner
    }
}

dependencies {
    implementation(Deps.Kotlin.stdlib)
    api(Deps.Kotlin.coroutines)
    api(Deps.Kotlin.androidCoroutines)
    api(Deps.AndroidSupport.appCompat)
    api(Deps.AndroidSupport.constraint)
    api(Deps.lottie)
}

project.configureLibraryPublication()



