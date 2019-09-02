package com.quickbirdstudios.survey_kit.public_api.steps

import android.content.Context
import com.quickbirdstudios.survey_kit.backend.views.step.StepView
import com.quickbirdstudios.survey_kit.public_api.StepIdentifier
import com.quickbirdstudios.survey_kit.public_api.result.StepResult

interface Step {
    val isOptional: Boolean
    val id: StepIdentifier
    fun createView(context: Context, stepResult: StepResult?): StepView
}
