import org.jetbrains.kotlin.gradle.dsl.KotlinCompile

buildscript {
    repositories {
        mavenCentral()
        jcenter()
        google()
        maven { url = uri("https://repo.quickbirdstudios.com/artifactory/public") }
    }

    dependencies {
        classpath(Deps.Plugins.kotlin)
        classpath(Deps.Plugins.android)
        classpath(Deps.Plugins.bintray)
    }
}

allprojects {
    repositories {
        mavenCentral()
        jcenter()
        google()
        maven { url = uri("https://jitpack.io") }
    }

    tasks.withType(KotlinCompile::class.java).all {
        kotlinOptions {
            freeCompilerArgs = listOf(
                "-Xuse-experimental=kotlin.Experimental",
                "-Xuse-experimental=kotlinx.coroutines.ObsoleteCoroutinesApi",
                "-Xuse-experimental=kotlinx.coroutines.ExperimentalCoroutinesApi"
            )
        }
    }
}
