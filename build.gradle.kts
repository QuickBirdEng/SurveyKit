import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile

buildscript {
    repositories {
        mavenCentral()
        google()
        maven { url = uri("https://repository.quickbirdstudios.com/repository/public") }
    }

    dependencies {
        classpath(Deps.Plugins.kotlin)
        classpath(Deps.Plugins.ktlint)
    }
}

allprojects {
    repositories {
        mavenCentral()
        google()
    }

    tasks.withType(KotlinJvmCompile::class.java).all {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = listOf(
                "-Xuse-experimental=kotlin.Experimental",
                "-Xuse-experimental=kotlinx.coroutines.ObsoleteCoroutinesApi",
                "-Xuse-experimental=kotlinx.coroutines.ExperimentalCoroutinesApi",
                "-Xuse-experimental=kotlin.ExperimentalStdlibApi"
            )
        }
    }
}