package com.quickbirdstudios.surveykit

import org.gradle.api.Project

object ApiKeys {

    fun Project.yandexMapsKey(): String {
        return propertyOrEmpty("yandex_sdk_key")
    }

    fun Project.googleMapsKey(): String {
        return propertyOrEmpty("google_sdk_key")
    }

    private fun Project.propertyOrEmpty(name: String): String {
        val property = findProperty(name) as String?
        return property ?: ""
    }
}
