package com.quickbirdstudios.surveykit

import Library
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction

private const val checkCiTagTaskName = "checkCiTag"

internal fun Project.configureCheckCiTagTaskIfNecessary() {
    tasks.register(checkCiTagTaskName, CheckCiTagTask::class.java)
}

internal fun Project.getCheckCiTagTask() = tasks.getByName(checkCiTagTaskName)

internal class AmbiguousCiTagException(message: String) : Exception(message)

internal class MissingCiTagException(message: String) : Exception(message)

internal open class CheckCiTagTask : DefaultTask() {

    @TaskAction
    fun checkCiTag() {
        val ciTag = System.getenv("CI_COMMIT_TAG") ?: throw MissingCiTagException(
            "Project can only be published by the CI after creating a new tag"
        )

        if (ciTag != "v${Library.version}") {
            throw AmbiguousCiTagException(
                "Project expected tag: v${Library.version}, but found: $ciTag"
            )
        }
    }
}
