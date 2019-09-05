package com.quickbirdstudios.survey_kit.steps

import android.content.Context
import com.quickbirdstudios.survey_kit.StepIdentifier
import com.quickbirdstudios.survey_kit.backend.views.step.StepView
import com.quickbirdstudios.survey_kit.result.StepResult

interface Step {
    val isOptional: Boolean
    val id: StepIdentifier
    fun createView(context: Context, stepResult: StepResult?): StepView
}
