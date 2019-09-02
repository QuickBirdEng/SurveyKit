package com.quickbirdstudios.survey_kit.backend.views.questions

import android.content.Context
import androidx.annotation.StringRes
import com.quickbirdstudios.survey.R
import com.quickbirdstudios.survey_kit.backend.helpers.extensions.afterTextChanged
import com.quickbirdstudios.survey_kit.backend.views.question_parts.TextField
import com.quickbirdstudios.survey_kit.backend.views.step.QuestionView
import com.quickbirdstudios.survey_kit.public_api.StepIdentifier
import com.quickbirdstudios.survey_kit.public_api.result.QuestionResult
import com.quickbirdstudios.survey_kit.public_api.result.question_results.TextQuestionResult

internal class TextQuestionView(
    context: Context,
    id: StepIdentifier,
    isOptional: Boolean,
    @StringRes title: Int?,
    @StringRes text: Int?,
    @StringRes nextButtonText: Int,
    private val hintText: Int = R.string.empty,
    private val preselected: String? = null
) : QuestionView(context, id, isOptional, title, text, nextButtonText) {


    //region Members

    private var questionAnswerView: TextField? = null

    //endregion


    //region Overrides

    override fun createResults(): QuestionResult = TextQuestionResult(
        id = id,
        startDate = startDate,
        answer = questionAnswerView?.field?.text?.toString(),
        stringIdentifier = questionAnswerView?.field?.text?.toString() ?: ""
    )

    override fun setState() {
        val textState = (state as? TextQuestionResult)?.answer ?: return
        questionAnswerView?.field?.setText(textState)
    }

    override fun isValidInput(): Boolean {
        val textField = questionAnswerView ?: return false
        return textField.field.text.isNotBlank()
    }

    //endregion


    //region Private API

    override fun setupViews() {
        super.setupViews()

        val textField = content.add(TextField.withHint(context, hintText))
        textField.field.afterTextChanged { footer.canContinue = isValidInput() }
        textField.field.setText(preselected)

        questionAnswerView = textField
    }

    //endregion


}
