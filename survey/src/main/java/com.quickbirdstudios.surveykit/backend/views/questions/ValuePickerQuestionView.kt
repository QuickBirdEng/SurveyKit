package com.quickbirdstudios.surveykit.backend.views.questions

import android.content.Context
import com.quickbirdstudios.surveykit.AnswerFormat
import com.quickbirdstudios.surveykit.StepIdentifier
import com.quickbirdstudios.surveykit.backend.views.question_parts.ValuePickerPart
import com.quickbirdstudios.surveykit.backend.views.step.QuestionView
import com.quickbirdstudios.surveykit.result.QuestionResult
import com.quickbirdstudios.surveykit.result.question_results.ValuePickerQuestionResult

internal class ValuePickerQuestionView(
    context: Context,
    id: StepIdentifier,
    isOptional: Boolean,
    title: String?,
    text: String?,
    nextButtonText: String,
    private val answerFormat: AnswerFormat.ValuePickerAnswerFormat,
    private val preselected: String?
) : QuestionView(context, id, isOptional, title, text, nextButtonText) {

    //region Members

    private lateinit var valuePicker: ValuePickerPart

    //endregion

    //region Overrides

    override fun createResults(): QuestionResult = ValuePickerQuestionResult(
        id = id,
        startDate = startDate,
        stringIdentifier = valuePicker.selected,
        answer = valuePicker.selected
    )

    override fun isValidInput(): Boolean = true

    override fun setupViews() {
        super.setupViews()

        valuePicker = content.add(ValuePickerPart(context))
        valuePicker.choices = answerFormat.choices
        answerFormat.defaultValue?.let { valuePicker.selected = it }
        preselected?.let { valuePicker.selected = it }
    }

    //endregion
}
