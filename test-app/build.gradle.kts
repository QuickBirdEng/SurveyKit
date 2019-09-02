plugins {
    id("com.android.application")
    kotlin("android")
    id("org.jetbrains.kotlin.android.extensions")
    id("quickbird")
}


quickbird {
    deploy {
        app {
            useOutputWithName("debug")
            googleDrive()
        }
    }
}

androidExtensions { isExperimental = true }

android {
    compileSdkVersion(Project.Android.compileSdkVersion)

    defaultConfig {
        versionName = Project.Version.name
        versionCode = Project.Version.code
        minSdkVersion(Project.Android.minSdkVersion)
        targetSdkVersion(Project.Android.targetSdkVersion)
        testInstrumentationRunner = Project.Android.testInstrumentationRunner
    }
    packagingOptions {
        exclude("META-INF/*kotlin*")
    }
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

    /* Test */
    implementation(Deps.Test.junit)
}
