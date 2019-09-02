object Versions {
    const val kotlin = "1.3.41"
    const val coroutines = "1.1.1"
    const val androidGradlePlugin = "3.4.2"
    const val quickbirdGradlePlugin = "0.0.13-rc02"

    const val junit = "4.12"

    const val realm = "5.10.0"

    const val lottie = "3.0.7"

    object AndroidSupport {
        const val appCompat = "1.0.0"
        const val constraintLayout = "1.1.3"
        const val annotation = "1.0.0"
    }
}

object Deps {

    object Plugins {
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
        const val android = "com.android.tools.build:gradle:${Versions.androidGradlePlugin}"
        const val realm = "io.realm:realm-gradle-plugin:${Versions.realm}"
        const val quickbird = "com.quickbirdstudios:gradle:${Versions.quickbirdGradlePlugin}"
    }

    object Kotlin {
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
        const val reflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"
        const val coroutines =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
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
    }

    const val lottie = "com.airbnb.android:lottie:${Versions.lottie}"
}
