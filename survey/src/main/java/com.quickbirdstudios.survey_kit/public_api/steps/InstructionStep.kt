package com.quickbirdstudios.survey_kit.public_api.steps

import android.content.Context
import androidx.annotation.StringRes
import com.quickbirdstudios.survey.R
import com.quickbirdstudios.survey_kit.backend.views.questions.IntroQuestionView
import com.quickbirdstudios.survey_kit.public_api.StepIdentifier
import com.quickbirdstudios.survey_kit.public_api.result.StepResult

open class InstructionStep(
    @StringRes private val title: Int? = null,
    @StringRes private val text: Int? = null,
    @StringRes private val buttonText: Int = R.string.start,
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
