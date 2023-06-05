package com.quickbirdstudios.surveykit.steps

import android.content.Context
import com.quickbirdstudios.surveykit.StepIdentifier
import com.quickbirdstudios.surveykit.backend.views.step.StepView
import com.quickbirdstudios.surveykit.result.StepResult

interface Step {
    val isOptional: Boolean
    val id: StepIdentifier
    fun createView(context: Context, stepResult: StepResult?): StepView
}
