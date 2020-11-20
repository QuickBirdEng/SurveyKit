package com.quickbirdstudios.surveykit

import java.util.*
import org.gradle.api.Project

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
        properties.load(project.rootProject.file("local.properties").inputStream())
        return properties[name] as? String?
    }

    private fun Project.findByProperties(name: String): String? {
        return properties[name] as String?
    }

    private fun String?.fallback(block: () -> String?): String? {
        return this ?: block()
    }
}
