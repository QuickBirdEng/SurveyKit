package com.quickbirdstudios.survey_kit.backend.views.questions

import android.content.Context
import androidx.annotation.StringRes
import com.quickbirdstudios.survey.R
import com.quickbirdstudios.survey_kit.backend.views.question_parts.SingleChoicePart
import com.quickbirdstudios.survey_kit.backend.views.step.QuestionView
import com.quickbirdstudios.survey_kit.public_api.SingleChoiceAnswerFormat
import com.quickbirdstudios.survey_kit.public_api.StepIdentifier
import com.quickbirdstudios.survey_kit.public_api.TextChoice
import com.quickbirdstudios.survey_kit.public_api.result.QuestionResult
import com.quickbirdstudios.survey_kit.public_api.result.question_results.SingleChoiceQuestionResult

internal class SingleChoiceQuestion(
    context: Context,
    id: StepIdentifier,
    isOptional: Boolean,
    @StringRes title: Int?,
    @StringRes text: Int?,
    @StringRes nextButtonText: Int,
    private val answerFormat: SingleChoiceAnswerFormat,
    private val preselected: TextChoice? = null
) : QuestionView(context, id, isOptional, title, text, nextButtonText) {


    //region Members

    private lateinit var choicesContainer: SingleChoicePart

    //endregion


    //region Overrides

    override fun createResults(): QuestionResult {
        val stringIdentifierRes = (answerFormat.textChoices
            .find { it.text == choicesContainer.selected?.text }
            ?.value
            ?: R.string.empty)

        return SingleChoiceQuestionResult(
            id = id,
            startDate = startDate,
            answer = choicesContainer.selected,
            stringIdentifier = context.getString(stringIdentifierRes)
        )
    }

    override fun setState() {}

    override fun isValidInput(): Boolean = choicesContainer.isOneSelected()

    //endregion


    //region Private API

    override fun setupViews() {
        super.setupViews()

        choicesContainer = content.add(SingleChoicePart(context))
        choicesContainer.options = answerFormat.textChoices
        choicesContainer.onCheckedChangeListener = { _, _ -> footer.canContinue = isValidInput() }
        choicesContainer.selected = preselected
    }

    //endregion

}
