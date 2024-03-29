package com.quickbirdstudios.surveykit.backend.views.questions

import android.content.Context
import android.view.Gravity
import androidx.annotation.StringRes
import com.quickbirdstudios.surveykit.AnswerFormat
import com.quickbirdstudios.surveykit.R
import com.quickbirdstudios.surveykit.StepIdentifier
import com.quickbirdstudios.surveykit.backend.helpers.extensions.afterTextChanged
import com.quickbirdstudios.surveykit.backend.views.question_parts.IntegerTextFieldPart
import com.quickbirdstudios.surveykit.backend.views.step.QuestionView
import com.quickbirdstudios.surveykit.extensions.getNonNullText
import com.quickbirdstudios.surveykit.result.QuestionResult
import com.quickbirdstudios.surveykit.result.question_results.IntegerQuestionResult

internal class IntegerQuestionView(
    context: Context,
    id: StepIdentifier,
    isOptional: Boolean,
    title: String?,
    text: String?,
    nextButtonText: String,
    skipButtonText: String,
    @StringRes private val hintText: Int = R.string.empty,
    private val answerFormat: AnswerFormat.IntegerAnswerFormat,
    private val preselected: Int? = null
) : QuestionView(context, id, isOptional, title, text, nextButtonText, skipButtonText) {

    //region Members

    private lateinit var questionAnswerView: IntegerTextFieldPart

    private var isValueChanged = false

    //endregion

    //region Overrides

    override fun createResults(): QuestionResult =
        IntegerQuestionResult(
            id = id,
            startDate = startDate,
            answer = questionAnswerView.field.text.toString().parseToIntOrNull(),
            stringIdentifier = questionAnswerView.field.text.toString()
        )

    override fun isValidInput(): Boolean = isOptional ||
            ((questionAnswerView.field.getNonNullText().isNotBlank()) &&
                    answerFormat.isValid(questionAnswerView.field.getNonNullText().toInt()))

    override fun setupViews() {
        super.setupViews()

        questionAnswerView = content.add(IntegerTextFieldPart.withHint(context, hintText))
        questionAnswerView.field.gravity = Gravity.CENTER
        questionAnswerView.field.hint = answerFormat.hint
        questionAnswerView.field.afterTextChanged {
            val isValidInput = isValidInput()
            footer.canContinue = isValidInput
            if (isValidInput || !isValueChanged) {
                questionAnswerView.fieldInfo.error = null
            } else {
                questionAnswerView.fieldInfo.error = answerFormat.errorText
            }
            isValueChanged = true
        }

        val alreadyEntered = preselected?.toString() ?: answerFormat.defaultValue?.toString()
        questionAnswerView.field.setText(alreadyEntered ?: "")
    }

    //endregion

    //region Private Helpers

    private fun String.parseToIntOrNull(): Int? = runCatching {
        this.toInt()
    }.getOrNull()

    //endregion
}
