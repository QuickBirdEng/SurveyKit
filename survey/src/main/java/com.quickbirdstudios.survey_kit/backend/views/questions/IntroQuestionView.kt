package com.quickbirdstudios.survey_kit.backend.views.questions

import android.content.Context
import androidx.annotation.StringRes
import com.quickbirdstudios.survey_kit.StepIdentifier
import com.quickbirdstudios.survey_kit.backend.views.step.QuestionView
import com.quickbirdstudios.survey_kit.result.question_results.IntroQuestionResult

class IntroQuestionView(
    context: Context,
    id: StepIdentifier,
    isOptional: Boolean = false,
    @StringRes private val title: Int?,
    @StringRes private val text: Int?,
    @StringRes private val startButtonText: Int
) : QuestionView(context, id, isOptional, title, text, startButtonText) {

    //region Overrides

    override fun createResults() =
        IntroQuestionResult(id, startDate)

    override fun isValidInput() = true

    override fun setupViews() {
        super.setupViews()

        header.canBack = false
        footer.questionCanBeSkipped = false
    }


    override fun setState() = Unit

    //endregion
}
