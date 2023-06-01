object Versions {

    const val kotlin = "1.6.2"
    const val kotlinPlugin = "1.6.20"
    const val coroutines = "1.6.2"
    const val test = "1.1.1"
    const val jUnit = "5.5.2"
    const val jUnitPlatform = "1.5.2"
    const val lottie = "3.0.7"
    const val espresso = "3.1.0"

    object AndroidSupport {
        const val appCompat = "1.3.0"
        const val constraintLayout = "2.0.4"
        const val annotation = "1.0.0"
        const val recyclerView = "1.2.1"
    }

    object PlayServices {
        const val maps = "17.0.0"
    }

    object Google {
        const val material = "1.4.0"
    }
}

object Deps {

    object Plugins {
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinPlugin}"
    }

    object Kotlin {
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
        const val reflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlinPlugin}"
        const val coroutines =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
        const val androidCoroutines =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    }

    object PlayServices {
        const val maps = "com.google.android.gms:play-services-maps:${Versions.PlayServices.maps}"
    }

    object Google {
        const val material = "com.google.android.material:material:${Versions.Google.material}"
    }

    object AndroidSupport {
        const val appCompat = "androidx.appcompat:appcompat:${Versions.AndroidSupport.appCompat}"
        const val constraint =
            "androidx.constraintlayout:constraintlayout:${Versions.AndroidSupport.constraintLayout}"
        const val recyclerView =
            "androidx.recyclerview:recyclerview:${Versions.AndroidSupport.recyclerView}"
    }

    object Test {
        const val jUnitJupiter = "org.junit.jupiter:junit-jupiter-engine:${Versions.jUnit}"
        const val jUnitPlatform =
            "org.junit.platform:junit-platform-runner:${Versions.jUnitPlatform}"
        const val core = "androidx.test:core:${Versions.test}"
        const val runner = "androidx.test:runner:${Versions.test}"
        const val rules = "androidx.test:rules:${Versions.test}"
        const val junitExt = "androidx.test.ext:junit:${Versions.test}"
        const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
        const val espressoIntents = "androidx.test.espresso:espresso-intents:${Versions.espresso}"
        const val espressoContribs = "androidx.test.espresso:espresso-contrib:${Versions.espresso}"
    }

    const val lottie = "com.airbnb.android:lottie:${Versions.lottie}"
}
