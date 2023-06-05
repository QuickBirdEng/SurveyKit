import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile

buildscript {
    repositories {
        maven { url = uri("https://repository.quickbirdstudios.com/repository/public") }
        google()
        mavenCentral()
    }

    dependencies {
        classpath(Deps.Plugins.kotlin)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
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