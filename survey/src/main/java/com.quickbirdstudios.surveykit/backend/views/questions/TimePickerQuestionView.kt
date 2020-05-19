package com.quickbirdstudios.surveykit.backend.views.questions

import android.content.Context
import com.quickbirdstudios.surveykit.AnswerFormat
import com.quickbirdstudios.surveykit.StepIdentifier
import com.quickbirdstudios.surveykit.backend.views.question_parts.TimePickerPart
import com.quickbirdstudios.surveykit.backend.views.step.QuestionView
import com.quickbirdstudios.surveykit.result.QuestionResult
import com.quickbirdstudios.surveykit.result.question_results.TimeQuestionResult

internal class TimePickerQuestionView(
    context: Context,
    id: StepIdentifier,
    isOptional: Boolean,
    title: String?,
    text: String?,
    nextButtonText: String,
    private val answerFormat: AnswerFormat.TimeAnswerFormat,
    private val preselected: AnswerFormat.TimeAnswerFormat.Time?
) : QuestionView(context, id, isOptional, title, text, nextButtonText) {

    //region Members

    private lateinit var timePicker: TimePickerPart

    //endregion

    //region Overrides

    override fun createResults(): QuestionResult = TimeQuestionResult(
        id = id,
        startDate = startDate,
        stringIdentifier = timePicker.selected.toTime().toString(),
        answer = timePicker.selected.toTime()
    )

    override fun isValidInput(): Boolean = true

    override fun setupViews() {
        super.setupViews()

        timePicker = content.add(TimePickerPart(context))
        answerFormat.defaultValue?.let { timePicker.selected = it.toSelected() }
        preselected?.let { timePicker.selected = it.toSelected() }
    }

    //endregion

    //region Priavte API

    private fun AnswerFormat.TimeAnswerFormat.Time.toSelected(): TimePickerPart.Selected =
        TimePickerPart.Selected(hour = this.hour, minute = this.minute)

    private fun TimePickerPart.Selected.toTime(): AnswerFormat.TimeAnswerFormat.Time =
        AnswerFormat.TimeAnswerFormat.Time(hour = this.hour, minute = this.minute)

    //endregion
}
