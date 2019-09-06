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

val sourcesJar2 by tasks.registering(Jar::class) {
    this.classifier = "sources"
    from(android.sourceSets.get("main").java.srcDirs)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = Library.groupId
            artifactId = Library.artifactId
            version = Library.version

            artifact(file("$buildDir/outputs/aar/survey-release.aar"))
            artifact(sourcesJar2.get())

            pom.withXml {
                asNode().appendNode("dependencies").apply {
                    configurations.api.get().dependencies.forEach {
                        val dependencyNode = appendNode("dependency")
                        dependencyNode.appendNode("groupId", it.group)
                        dependencyNode.appendNode("artifactId", it.name)
                        dependencyNode.appendNode("version", it.version)
                    }
                }
            }
        }
    }
}
