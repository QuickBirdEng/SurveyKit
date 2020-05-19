package com.quickbirdstudios.surveykit.backend.views.questions

import android.content.Context
import com.quickbirdstudios.surveykit.FinishReason
import com.quickbirdstudios.surveykit.StepIdentifier
import com.quickbirdstudios.surveykit.backend.views.question_parts.QuestionAnimation
import com.quickbirdstudios.surveykit.backend.views.step.QuestionView
import com.quickbirdstudios.surveykit.result.question_results.FinishQuestionResult
import com.quickbirdstudios.surveykit.steps.CompletionStep

internal class FinishQuestionView(
    context: Context,
    id: StepIdentifier = StepIdentifier(),
    title: String?,
    text: String?,
    finishButtonText: String,
    private val lottieAnimation: CompletionStep.LottieAnimation?,
    private val repeatCount: Int?
) : QuestionView(context, id, false, title, text, finishButtonText) {

    //region Overrides

    override fun createResults() =
        FinishQuestionResult(id, startDate)

    override fun isValidInput() = true

    override fun setupViews() {
        super.setupViews()
        content.add(
            QuestionAnimation(
                context = context,
                animation = lottieAnimation,
                repeatCount = repeatCount
            )
        )

        footer.questionCanBeSkipped = false
        footer.onContinue = { onCloseListener(createResults(), FinishReason.Completed) }
    }

    //endregion
}
