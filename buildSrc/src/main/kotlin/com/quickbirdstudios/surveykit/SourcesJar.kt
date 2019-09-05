package com.quickbirdstudios.surveykit

import com.android.build.gradle.BaseExtension
import org.gradle.api.Project
import org.gradle.jvm.tasks.Jar
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.register

private const val sourcesJarTaskName = "sourcesJar"

private typealias AndroidBaseExtension = BaseExtension

fun Project.configureSourcesJarTaskIfNecessary() {
    if (tasks.findByName(sourcesJarTaskName) != null) {
        return
    }

    val extensions = extensions
    tasks.register(sourcesJarTaskName, Jar::class) {
        archiveClassifier.set("sources")
        from(extensions.getByType(AndroidBaseExtension::class).sourceSets["main"].java.srcDirs)
    }
}

fun Project.getSourcesJarTask() = tasks.getByName(sourcesJarTaskName)
