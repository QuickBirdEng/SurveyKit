package com.quickbirdstudios.surveykit.backend.views.questions

import android.content.Context
import android.text.InputType
import com.quickbirdstudios.surveykit.AnswerFormat
import com.quickbirdstudios.surveykit.StepIdentifier
import com.quickbirdstudios.surveykit.backend.helpers.extensions.afterTextChanged
import com.quickbirdstudios.surveykit.backend.views.question_parts.TextFieldPart
import com.quickbirdstudios.surveykit.backend.views.step.QuestionView
import com.quickbirdstudios.surveykit.result.QuestionResult
import com.quickbirdstudios.surveykit.result.question_results.EmailQuestionResult

internal class EmailQuestionView(
    context: Context,
    id: StepIdentifier,
    isOptional: Boolean,
    title: String?,
    text: String?,
    nextButtonText: String,
    private val answerFormat: AnswerFormat.EmailAnswerFormat,
    private val preselected: String? = null
) : QuestionView(context, id, isOptional, title, text, nextButtonText) {

    //region Members

    private lateinit var emailField: TextFieldPart

    //endregion

    //region Overrides

    override fun createResults(): QuestionResult = EmailQuestionResult(
        id = id,
        startDate = startDate,
        answer = emailField.field.text.toString(),
        stringIdentifier = emailField.field.text.toString()
    )

    override fun isValidInput(): Boolean {
        return answerFormat.isValid(emailField.field.text.toString())
    }

    override fun setupViews() {
        super.setupViews()

        emailField = content.add(
            TextFieldPart.withHint(context, answerFormat.hintText ?: "")
        )
        emailField.field.apply {
            inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            maxLines = 1
            textAlignment = TEXT_ALIGNMENT_CENTER
            afterTextChanged { footer.canContinue = isValidInput() }
            setText(preselected)
        }
    }

    //endregion
}
