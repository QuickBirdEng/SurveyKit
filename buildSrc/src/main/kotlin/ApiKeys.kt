package com.quickbirdstudios.surveykit

import org.gradle.api.Project
import java.io.IOException
import java.util.*

object ApiKeys {

    fun Project.yandexMapsKey(): String {
        return propertyOrEmpty("yandex_sdk_key")
    }

    fun Project.googleMapsKey(): String {
        return propertyOrEmpty("google_sdk_key")
    }

    private fun Project.propertyOrEmpty(name: String): String {
        return findPropertyCasted(name)
            .fallback { findInLocalPropertiesFile(name) }
            .fallback { findByProperties(name) } ?: ""
    }

    private fun Project.findPropertyCasted(name: String): String? = findProperty(name) as String?

    private fun Project.findInLocalPropertiesFile(name: String): String? {
        val properties = Properties()
        try {
            properties.load(project.rootProject.file("local.properties").inputStream())
        } catch (e: IOException) {
            println("Error trying to load local.properties file")
        }
        return properties[name] as? String?
    }

    private fun Project.findByProperties(name: String): String? {
        return properties[name] as String?
    }

    private fun String?.fallback(block: () -> String?): String? {
        return this ?: block()
    }
}
