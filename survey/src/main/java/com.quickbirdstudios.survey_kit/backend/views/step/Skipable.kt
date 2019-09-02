package com.quickbirdstudios.survey_kit.backend.views.step

interface Skipable {
    val isOptional: Boolean
    fun onSkip(block: () -> Unit)
}
