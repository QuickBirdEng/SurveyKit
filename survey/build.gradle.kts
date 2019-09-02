import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.library")
    kotlin("android")
    id("org.jetbrains.kotlin.android.extensions")
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
    implementation(Deps.Kotlin.reflect)
    implementation(Deps.Kotlin.coroutines)
    implementation(Deps.AndroidSupport.annotations)
    implementation(Deps.AndroidSupport.appCompat)
    implementation(Deps.AndroidSupport.constraint)

    implementation(Deps.lottie)
}


tasks.withType<KotlinCompile>().all {
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs += "-Xuse-experimental=kotlin.ExperimentalStdlibApi"
    }
}
