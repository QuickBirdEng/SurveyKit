package com.quickbirdstudios.surveykit.backend.views.questions

import android.content.Context
import androidx.annotation.StringRes
import com.quickbirdstudios.survey.R
import com.quickbirdstudios.surveykit.AnswerFormat
import com.quickbirdstudios.surveykit.StepIdentifier
import com.quickbirdstudios.surveykit.backend.helpers.extensions.afterTextChanged
import com.quickbirdstudios.surveykit.backend.views.question_parts.TextField
import com.quickbirdstudios.surveykit.backend.views.step.QuestionView
import com.quickbirdstudios.surveykit.result.QuestionResult
import com.quickbirdstudios.surveykit.result.question_results.EmailQuestionResult

internal class EmailQuestionView(
    context: Context,
    id: StepIdentifier,
    isOptional: Boolean,
    @StringRes title: Int?,
    @StringRes text: Int?,
    @StringRes nextButtonText: Int,
    private val answerFormat: AnswerFormat.EmailAnswerFormat,
    private val preselected: String? = null
) : QuestionView(context, id, isOptional, title, text, nextButtonText) {

    //region Members

    private lateinit var emailField: TextField

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
            TextField.withHint(context, answerFormat.hintText ?: R.string.empty)
        )
        emailField.field.maxLines = 1
        emailField.field.textAlignment = TEXT_ALIGNMENT_CENTER
        emailField.field.afterTextChanged { footer.canContinue = isValidInput() }
        emailField.field.setText(preselected)
    }

    //endregion


}
