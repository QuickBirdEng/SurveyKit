@file:Suppress("UnstableApiUsage")

package com.quickbirdstudios.surveykit

import Library
import com.jfrog.bintray.gradle.BintrayExtension
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.register

private const val bintrayUserKey = "bintray_user"
private const val bintrayApiKeyKey = "bintray_apikey"


fun Project.configureLibraryPublication() {
    configureSourcesJarTaskIfNecessary()
    configureCheckCiTagTaskIfNecessary()
    configureLibraryAarPublication()
    configureBintrayForLibraryPublication()
    configurePublishTask()
}


internal fun Project.configurePublishTask() = afterEvaluate {
    val preBuild = tasks["preBuild"]
    val publish = tasks["publish"]
    val bintrayUpload = tasks["bintrayUpload"]
    val assembleRelease = tasks["assembleRelease"]
    val publishAarPublicationToMavenLocal = tasks["publishAarPublicationToMavenLocal"]
    val checkCiTagTask = getCheckCiTagTask()

    publishAarPublicationToMavenLocal.dependsOn(checkCiTagTask)
    publishAarPublicationToMavenLocal.dependsOn(assembleRelease)
    publish.dependsOn(bintrayUpload)
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
                    configurations["api"].dependencies.forEach { dependency ->
                        val dependencyNode = appendNode("dependency")
                        dependencyNode.appendNode("scope", "runtime")
                        dependencyNode.appendNode("groupId", dependency.group)
                        dependencyNode.appendNode("artifactId", dependency.name)
                        dependencyNode.appendNode("version", dependency.version)
                    }
                }
            }
        }
    }
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


private fun Project.bintrayUser(): String {
    return environmentPropertyOrStub(bintrayUserKey)
}

private fun Project.bintrayKey(): String {
    return environmentPropertyOrStub(bintrayApiKeyKey)
}

private fun Project.environmentPropertyOrStub(key: String): String {
    return System.getenv(key) ?: project.properties.getOrDefault(key, "stub").toString()
}