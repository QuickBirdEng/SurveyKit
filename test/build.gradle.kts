import com.quickbirdstudios.surveykit.ApiKeys.googleMapsKey

plugins {
    id("com.android.application")
    kotlin("android")
    id("org.jetbrains.kotlin.android.extensions")
}

androidExtensions { isExperimental = true }

android {
    compileSdkVersion(Project.Android.compileSdkVersion)

    defaultConfig {
        versionName = Library.version
        versionCode = 1
        minSdkVersion(Project.Android.minSdkVersion)
        targetSdkVersion(Project.Android.targetSdkVersion)
        testInstrumentationRunner = Project.Android.testInstrumentationRunner
        resValue("string", "google_api_key", googleMapsKey())
    }
    packagingOptions {
        jniLibs {
            excludes += setOf("META-INF/*kotlin*")
        }
        resources {
            excludes += setOf(
                "META-INF/*kotlin*",
                "META-INF/LICENSE.md",
                "META-INF/LICENSE-notice.md"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    testOptions {
        animationsDisabled = true
    }
    namespace = "com.quickbirdstudios.test"
}

dependencies {
    implementation(project(":survey"))

    implementation(Deps.Test.core)
    implementation(Deps.Test.rules)
    implementation(Deps.Test.runner)
    implementation(Deps.Test.jUnitJupiter)
    implementation(Deps.Test.jUnitPlatform)
    implementation(Deps.Test.junitExt)
    implementation(Deps.Test.espresso)
    implementation(Deps.Test.espressoContribs)
}
