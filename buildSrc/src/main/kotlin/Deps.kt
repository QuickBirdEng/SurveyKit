object Versions {
    const val kotlin = "1.3.50"
    const val coroutines = "1.3.0"
    const val junit = "4.12"
    const val espresso = "3.1.0"
    const val rules = "1.2.0"
    const val lottie = "3.0.7"
    const val testInstrumentationRunner = "1.1.0"

    object AndroidSupport {
        const val appCompat = "1.0.0"
        const val constraintLayout = "1.1.3"
        const val annotation = "1.0.0"
    }
}

object Deps {

    object Plugins {
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    }

    object Kotlin {
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
        const val reflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"
        const val coroutines =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
        const val androidCoroutines =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    }

    object AndroidSupport {
        const val appCompat = "androidx.appcompat:appcompat:${Versions.AndroidSupport.appCompat}"
        const val constraint =
            "androidx.constraintlayout:constraintlayout:${Versions.AndroidSupport.constraintLayout}"
        const val annotations =
            "androidx.annotation:annotation:${Versions.AndroidSupport.annotation}"
    }

    object Test {
        const val junit = "junit:junit:${Versions.junit}"
        val testRunner = "androidx.test:runner:${Versions.testInstrumentationRunner}"
        val testOrchestrator =
            "androidx.test:orchestrator:${Versions.testInstrumentationRunner}"
        const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"
        const val rules = "androidx.test:rules:${Versions.rules}"
    }

    const val lottie = "com.airbnb.android:lottie:${Versions.lottie}"
}
