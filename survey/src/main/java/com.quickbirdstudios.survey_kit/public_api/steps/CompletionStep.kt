package com.quickbirdstudios.survey_kit.public_api.steps

import android.content.Context
import androidx.annotation.StringRes
import com.quickbirdstudios.survey.R
import com.quickbirdstudios.survey_kit.backend.views.questions.FinishQuestionView
import com.quickbirdstudios.survey_kit.public_api.StepIdentifier
import com.quickbirdstudios.survey_kit.public_api.result.StepResult

class CompletionStep(
    @StringRes private val title: Int? = null,
    @StringRes private val text: Int? = null,
    @StringRes private val buttonText: Int = R.string.finish,
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

