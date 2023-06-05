package com.quickbirdstudios.surveykit.steps

import android.content.Context
import com.quickbirdstudios.surveykit.StepIdentifier
import com.quickbirdstudios.surveykit.backend.views.questions.IntroQuestionView
import com.quickbirdstudios.surveykit.result.StepResult

open class InstructionStep(
    private val title: String? = null,
    private val text: String? = null,
    private val buttonText: String = "Start",
    override var isOptional: Boolean = false,
    override val id: StepIdentifier = StepIdentifier()
) : Step {
    override fun createView(context: Context, stepResult: StepResult?) =
        IntroQuestionView(
            context = context,
            id = id,
            isOptional = isOptional,
            title = title,
            text = text,
            startButtonText = buttonText
        )
}
