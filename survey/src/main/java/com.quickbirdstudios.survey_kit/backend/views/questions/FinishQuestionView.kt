package com.quickbirdstudios.survey_kit.backend.views.questions

import android.content.Context
import androidx.annotation.StringRes
import com.quickbirdstudios.survey_kit.backend.views.question_parts.AnimatedCheckmark
import com.quickbirdstudios.survey_kit.backend.views.step.QuestionView
import com.quickbirdstudios.survey_kit.public_api.FinishReason
import com.quickbirdstudios.survey_kit.public_api.StepIdentifier
import com.quickbirdstudios.survey_kit.public_api.result.question_results.FinishQuestionResult

//TODO pass title and text into constructor since it can not be changed afterwards.
class FinishQuestionView(
    context: Context,
    id: StepIdentifier = StepIdentifier(),
    @StringRes private val title: Int?,
    @StringRes private val text: Int?,
    @StringRes private val finishButtonText: Int
) : QuestionView(context, id, false, title, text, finishButtonText) {

    //region Overrides

    override fun createResults() = FinishQuestionResult(id, startDate)

    override fun setState() {}

    override fun isValidInput() = true

    override fun setupViews() {
        super.setupViews()

        content.clear()
        content.add(AnimatedCheckmark(context))

        footer.questionCanBeSkipped = false
        footer.onContinue = { onCloseListener(createResults(), FinishReason.Completed) }
    }

    //endregion

}
