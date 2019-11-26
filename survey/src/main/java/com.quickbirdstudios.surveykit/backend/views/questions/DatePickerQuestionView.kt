package com.quickbirdstudios.surveykit.backend.views.questions

import android.content.Context
import androidx.annotation.StringRes
import com.quickbirdstudios.surveykit.AnswerFormat
import com.quickbirdstudios.surveykit.StepIdentifier
import com.quickbirdstudios.surveykit.backend.views.question_parts.DatePickerPart
import com.quickbirdstudios.surveykit.backend.views.step.QuestionView
import com.quickbirdstudios.surveykit.result.QuestionResult
import com.quickbirdstudios.surveykit.result.question_results.DateQuestionResult

internal class DatePickerQuestionView(
    context: Context,
    id: StepIdentifier,
    isOptional: Boolean,
    @StringRes title: Int?,
    @StringRes text: Int?,
    @StringRes nextButtonText: Int,
    private val answerFormat: AnswerFormat.DateAnswerFormat,
    private val preselected: AnswerFormat.DateAnswerFormat.Date?
) : QuestionView(context, id, isOptional, title, text, nextButtonText) {

    //region Members

    private lateinit var datePicker: DatePickerPart

    //endregion

    //region Overrides

    override fun createResults(): QuestionResult = DateQuestionResult(
        id = id,
        startDate = startDate,
        stringIdentifier = datePicker.selected.toDate().toString(),
        answer = datePicker.selected.toDate()
    )

    override fun isValidInput(): Boolean = true

    override fun setupViews() {
        super.setupViews()

        datePicker = content.add(DatePickerPart(context))
        answerFormat.defaultValue?.let { datePicker.selected = it.toSelected() }
        preselected?.let { datePicker.selected = it.toSelected() }
    }

    //endregion

    //region Private API

    private fun AnswerFormat.DateAnswerFormat.Date.toSelected(): DatePickerPart.Selection =
        DatePickerPart.Selection(day = this.day, month = this.month, year = this.year)

    private fun DatePickerPart.Selection.toDate(): AnswerFormat.DateAnswerFormat.Date =
        AnswerFormat.DateAnswerFormat.Date(day = this.day, month = this.month, year = this.year)

    //endregion

}
