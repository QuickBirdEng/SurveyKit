package com.quickbirdstudios.surveykit.backend.views.questions

import android.content.Context
import androidx.annotation.StringRes
import com.quickbirdstudios.surveykit.FinishReason
import com.quickbirdstudios.surveykit.StepIdentifier
import com.quickbirdstudios.surveykit.backend.views.question_parts.AnimatedCheckmark
import com.quickbirdstudios.surveykit.backend.views.step.QuestionView
import com.quickbirdstudios.surveykit.result.question_results.FinishQuestionResult

class FinishQuestionView(
    context: Context,
    id: StepIdentifier = StepIdentifier(),
    @StringRes private val title: Int?,
    @StringRes private val text: Int?,
    @StringRes private val finishButtonText: Int
) : QuestionView(context, id, false, title, text, finishButtonText) {

    //region Overrides

    override fun createResults() =
        FinishQuestionResult(id, startDate)

    override fun isValidInput() = true

    override fun setupViews() {
        super.setupViews()

        content.add(
            AnimatedCheckmark(
                context
            )
        )

        footer.questionCanBeSkipped = false
        footer.onContinue = { onCloseListener(createResults(), FinishReason.Completed) }
    }

    //endregion

}
