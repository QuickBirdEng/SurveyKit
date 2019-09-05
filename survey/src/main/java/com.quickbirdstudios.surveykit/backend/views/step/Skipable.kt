package com.quickbirdstudios.surveykit.backend.views.step

interface Skipable {
    val isOptional: Boolean
    fun onSkip(block: () -> Unit)
}
