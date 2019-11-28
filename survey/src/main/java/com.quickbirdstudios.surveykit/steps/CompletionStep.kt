package com.quickbirdstudios.surveykit.steps

import android.content.Context
import com.quickbirdstudios.surveykit.StepIdentifier
import com.quickbirdstudios.surveykit.backend.views.questions.FinishQuestionView
import com.quickbirdstudios.surveykit.result.StepResult

class CompletionStep(
    private val title: String? = null,
    private val text: String? = null,
    private val buttonText: String = "Finish",
    override val isOptional: Boolean = false,
    override val id: StepIdentifier = StepIdentifier()
) : Step {
    override fun createView(context: Context, stepResult: StepResult?) =
        FinishQuestionView(
            context = context,
            title = title,
            text = text,
            finishButtonText = buttonText
        )
}

