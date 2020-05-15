package com.quickbirdstudios.surveykit.backend.views.questions

import android.content.Context
import com.quickbirdstudios.surveykit.AnswerFormat
import com.quickbirdstudios.surveykit.StepIdentifier
import com.quickbirdstudios.surveykit.TextChoice
import com.quickbirdstudios.surveykit.backend.views.question_parts.SingleChoicePart
import com.quickbirdstudios.surveykit.backend.views.step.QuestionView
import com.quickbirdstudios.surveykit.result.QuestionResult
import com.quickbirdstudios.surveykit.result.question_results.SingleChoiceQuestionResult

internal class SingleChoiceQuestionView(
    context: Context,
    id: StepIdentifier,
    isOptional: Boolean,
    title: String?,
    text: String?,
    nextButtonText: String,
    private val answerFormat: AnswerFormat.SingleChoiceAnswerFormat,
    private val preselected: TextChoice? = null
) : QuestionView(context, id, isOptional, title, text, nextButtonText) {

    //region Members

    private lateinit var choicesContainer: SingleChoicePart

    //endregion

    //region Overrides

    override fun createResults(): QuestionResult {
        val stringIdentifier = (answerFormat.textChoices
            .find { it.text == choicesContainer.selected?.text }
            ?.value
            ?: "")

        return SingleChoiceQuestionResult(
            id = id,
            startDate = startDate,
            answer = choicesContainer.selected,
            stringIdentifier = stringIdentifier
        )
    }

    override fun isValidInput(): Boolean = choicesContainer.isOneSelected()

    //endregion

    //region Private API

    override fun setupViews() {
        super.setupViews()

        choicesContainer = content.add(SingleChoicePart(context))
        choicesContainer.options = answerFormat.textChoices
        choicesContainer.onCheckedChangeListener = { _, _ -> footer.canContinue = isValidInput() }
        choicesContainer.selected = preselected ?: answerFormat.defaultSelection
    }

    //endregion
}
