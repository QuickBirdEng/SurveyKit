package com.quickbirdstudios.surveykit.backend.views.questions

import android.content.Context
import android.view.Gravity
import androidx.annotation.StringRes
import com.quickbirdstudios.surveykit.AnswerFormat
import com.quickbirdstudios.surveykit.R
import com.quickbirdstudios.surveykit.StepIdentifier
import com.quickbirdstudios.surveykit.backend.helpers.extensions.afterTextChanged
import com.quickbirdstudios.surveykit.backend.views.question_parts.DoubleTextFieldPart
import com.quickbirdstudios.surveykit.backend.views.step.QuestionView
import com.quickbirdstudios.surveykit.result.QuestionResult
import com.quickbirdstudios.surveykit.result.question_results.DoubleQuestionResult

internal class DoubleQuestionView(
    context: Context,
    id: StepIdentifier,
    isOptional: Boolean,
    title: String?,
    text: String?,
    nextButtonText: String,
    @StringRes private val hintText: Int = R.string.empty,
    private val answerFormat: AnswerFormat.DoubleAnswerFormat,
    private val preselected: Double? = null
) : QuestionView(context, id, isOptional, title, text, nextButtonText) {

    //region Members
    private lateinit var questionAnswerView: DoubleTextFieldPart
    //endregion

    //region Overrides
    override fun createResults(): QuestionResult =
        DoubleQuestionResult(
            id = id,
            startDate = startDate,
            answer = questionAnswerView.field.text.toString().parseToDoubleOrNull(),
            stringIdentifier = questionAnswerView.field.text.toString()
        )

    override fun isValidInput(): Boolean = isOptional || questionAnswerView.field.text.isNotBlank()

    override fun setupViews() {
        super.setupViews()

        questionAnswerView = content.add(DoubleTextFieldPart.withHint(context, hintText))
        questionAnswerView.field.gravity = Gravity.CENTER
        questionAnswerView.field.hint = answerFormat.hint
        questionAnswerView.field.afterTextChanged { footer.canContinue = isValidInput() }
        val preselected = preselected?.toString() ?: answerFormat.defaultValue?.toString()
        questionAnswerView.field.setText(preselected ?: context.getString(R.string.empty))
    }
    //endregion

    //region Private Helpers
    private fun String.parseToDoubleOrNull(): Double? {
        return try {
            this.toDouble()
        } catch (e: NumberFormatException) {
            null
        }
    }
    //endregion
}
