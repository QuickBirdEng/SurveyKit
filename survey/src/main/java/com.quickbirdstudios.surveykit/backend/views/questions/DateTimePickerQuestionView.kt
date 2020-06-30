package com.quickbirdstudios.surveykit.backend.views.questions

import android.content.Context
import com.quickbirdstudios.surveykit.AnswerFormat
import com.quickbirdstudios.surveykit.StepIdentifier
import com.quickbirdstudios.surveykit.backend.views.question_parts.DateTimePickerPart
import com.quickbirdstudios.surveykit.backend.views.step.QuestionView
import com.quickbirdstudios.surveykit.result.QuestionResult
import com.quickbirdstudios.surveykit.result.question_results.DateTimeQuestionResult

internal class DateTimePickerQuestionView(
    context: Context,
    id: StepIdentifier,
    isOptional: Boolean,
    title: String?,
    text: String?,
    nextButtonText: String,
    private val answerFormat: AnswerFormat.DateTimeAnswerFormat,
    private val preselected: AnswerFormat.DateTimeAnswerFormat.DateTime?
) : QuestionView(context, id, isOptional, title, text, nextButtonText) {

    private lateinit var dateTimePickerPart: DateTimePickerPart

    override fun createResults(): QuestionResult = DateTimeQuestionResult(
        id = id,
        startDate = startDate,
        stringIdentifier = dateTimePickerPart.toDateTime().toString(),
        answer = dateTimePickerPart.toDateTime()
    )

    override fun setupViews() {
        super.setupViews()

        dateTimePickerPart = content.add(DateTimePickerPart(context))
        answerFormat.defaultValue?.let {
            dateTimePickerPart.selectedTime = it.toTime()
            dateTimePickerPart.selectedDate = it.toDate()
        }
        preselected?.let {
            dateTimePickerPart.selectedTime = it.toTime()
            dateTimePickerPart.selectedDate = it.toDate()
        }
    }

    override fun isValidInput(): Boolean = true

    private fun AnswerFormat.DateTimeAnswerFormat.DateTime.toTime(): DateTimePickerPart.SelectedTime =
        DateTimePickerPart.SelectedTime(hour = this.hour, minute = this.minute)

    private fun AnswerFormat.DateTimeAnswerFormat.DateTime.toDate(): DateTimePickerPart.SelectedDate =
        DateTimePickerPart.SelectedDate(year = year, month = month, dayOfMonth = day)

    private fun DateTimePickerPart.toDateTime(): AnswerFormat.DateTimeAnswerFormat.DateTime =
        AnswerFormat.DateTimeAnswerFormat.DateTime(
            day = selectedDate.dayOfMonth,
            month = selectedDate.month,
            year = selectedDate.year,
            hour = selectedTime.hour,
            minute = selectedTime.minute
        )
}
