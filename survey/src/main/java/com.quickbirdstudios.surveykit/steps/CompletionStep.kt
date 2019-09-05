package com.quickbirdstudios.surveykit.steps

import android.content.Context
import androidx.annotation.StringRes
import com.quickbirdstudios.survey.R
import com.quickbirdstudios.surveykit.StepIdentifier
import com.quickbirdstudios.surveykit.backend.views.questions.FinishQuestionView
import com.quickbirdstudios.surveykit.result.StepResult

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

