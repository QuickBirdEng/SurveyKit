package com.quickbirdstudios.surveykit.backend.views.questions

import android.content.Context
import androidx.annotation.StringRes
import com.quickbirdstudios.survey.R
import com.quickbirdstudios.surveykit.AnswerFormat
import com.quickbirdstudios.surveykit.StepIdentifier
import com.quickbirdstudios.surveykit.TextChoice
import com.quickbirdstudios.surveykit.backend.views.question_parts.SingleChoicePart
import com.quickbirdstudios.surveykit.backend.views.step.QuestionView
import com.quickbirdstudios.surveykit.result.QuestionResult
import com.quickbirdstudios.surveykit.result.question_results.BooleanQuestionResult

internal class BooleanQuestionView(
    context: Context,
    id: StepIdentifier,
    isOptional: Boolean,
    @StringRes title: Int?,
    @StringRes text: Int?,
    @StringRes nextButtonText: Int,
    private val answerFormat: AnswerFormat.BooleanAnswerFormat,
    private var preselected: AnswerFormat.BooleanAnswerFormat.Result?
) : QuestionView(context, id, isOptional, title, text, nextButtonText) {


    //region Members

    private lateinit var booleanAnswerPart: SingleChoicePart

    //endregion

    //region Overrides

    override fun createResults(): QuestionResult {
        val stringIdentifierRes = (answerFormat.textChoices
            .find { it.text == booleanAnswerPart.selected?.text }
            ?.value
            ?: R.string.empty)

        return BooleanQuestionResult(
            id = id,
            startDate = startDate,
            answer = answerFormat.toResult(booleanAnswerPart.selected?.text),
            stringIdentifier = context.getString(stringIdentifierRes)
        )
    }

    override fun isValidInput(): Boolean = booleanAnswerPart.isOneSelected()

    override fun setState() { }

    override fun setupViews() {
        super.setupViews()

        val selected = preselected ?: answerFormat.defaultValue

        booleanAnswerPart = content.add(SingleChoicePart(context))
        booleanAnswerPart.options = answerFormat.textChoices
        booleanAnswerPart.onCheckedChangeListener = { _, _ -> footer.canContinue = isValidInput() }
        booleanAnswerPart.selected = selected.toSelectedTextChoice(answerFormat)
    }

    //endregion


    //region Private Helper

    private fun AnswerFormat.BooleanAnswerFormat.Result?.toSelectedTextChoice(
        answerFormat: AnswerFormat.BooleanAnswerFormat
    ): TextChoice? {
        val positiveStringId = answerFormat.positiveAnswerText
        val negativeStringId = answerFormat.negativeAnswerText
        return when (this) {
            AnswerFormat.BooleanAnswerFormat.Result.None -> null
            AnswerFormat.BooleanAnswerFormat.Result.PositiveAnswer -> TextChoice(positiveStringId)
            AnswerFormat.BooleanAnswerFormat.Result.NegativeAnswer -> TextChoice(negativeStringId)
            else -> null
        }
    }

    //endregion
}
