@file:Suppress("UnstableApiUsage")

package com.quickbirdstudios.surveykit

import Library
import com.jfrog.bintray.gradle.BintrayExtension
import groovy.util.Node
import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.register

private const val bintrayUserKey = "bintray_user"
private const val bintrayApiKeyKey = "bintray_apikey"

fun Project.configureLibraryPublication() {
    configureSourcesJarTaskIfNecessary()
//    configureCheckCiTagTaskIfNecessary()
    configureLibraryAarPublication()
    configureBintrayForLibraryPublication()
    configurePublishTask()
}

internal fun Project.configureLibraryAarPublication() {
    val projectName = name
    extensions.getByType<PublishingExtension>().publications {
        register<MavenPublication>("aar") {
            groupId = Library.groupId
            artifactId = Library.artifactId
            version = Library.version

            artifact(file("$buildDir/outputs/aar/$projectName-release.aar"))
            artifact(getSourcesJarTask())

            pom.withXml {
                asNode().appendNode("dependencies").apply {
                    configurations["implementation"].dependencies.forEach { dependency ->
                        val dependencyNode = appendNode("dependency")
                        dependencyNode.appendDependency(dependency, scope = "runtime")
                    }
                    configurations["api"].dependencies.forEach { dependency ->
                        val dependencyNode = appendNode("dependency")
                        dependencyNode.appendDependency(dependency)
                    }
                }
            }
        }
    }
}

internal fun Node.appendDependency(dependency: Dependency, scope: String? = null) {
    if (scope != null) {
        appendNode("scope", scope)
    }
    appendNode("groupId", dependency.group)
    appendNode("artifactId", dependency.name)
    appendNode("version", dependency.version)
}

internal fun Project.configureBintrayForLibraryPublication() =
    extensions.getByType<BintrayExtension>().run {
        user = bintrayUser()
        key = bintrayKey()
        setPublications("aar")
        pkg.run {
            name = Library.Bintray.packageName
            repo = Library.Bintray.repository
            userOrg = Library.Bintray.organization
            websiteUrl = Library.Meta.websiteUrl
            vcsUrl = Library.Meta.gitUrl
            setLicenses(*Library.Bintray.allLicenses)
            publish = true
            version.name = Library.version
            version.gpg.sign = false
        }
    }

internal fun Project.configurePublishTask() = afterEvaluate {
    val publish = tasks["publish"]
    val bintrayUpload = tasks["bintrayUpload"]
    val assembleRelease = tasks["assembleRelease"]
    val publishAarPublicationToMavenLocal = tasks["publishAarPublicationToMavenLocal"]
//    val checkCiTagTask = getCheckCiTagTask()

//    publishAarPublicationToMavenLocal.dependsOn(checkCiTagTask)
    publishAarPublicationToMavenLocal.dependsOn(assembleRelease)
    publish.dependsOn(bintrayUpload)
}

private fun Project.bintrayUser(): String {
    return environmentVariableOrPropertyOrStub(bintrayUserKey)
}

private fun Project.bintrayKey(): String {
    return environmentVariableOrPropertyOrStub(bintrayApiKeyKey)
}

private fun Project.environmentVariableOrPropertyOrStub(key: String): String {
    return System.getenv(key) ?: project.properties.getOrDefault(key, "stub").toString()
}
