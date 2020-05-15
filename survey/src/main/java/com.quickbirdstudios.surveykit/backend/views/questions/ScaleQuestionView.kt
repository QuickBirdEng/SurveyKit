package com.quickbirdstudios.surveykit.backend.views.questions

import android.content.Context
import com.quickbirdstudios.surveykit.AnswerFormat
import com.quickbirdstudios.surveykit.StepIdentifier
import com.quickbirdstudios.surveykit.backend.views.question_parts.ScalePart
import com.quickbirdstudios.surveykit.backend.views.step.QuestionView
import com.quickbirdstudios.surveykit.result.QuestionResult
import com.quickbirdstudios.surveykit.result.question_results.ScaleQuestionResult

internal class ScaleQuestionView(
    context: Context,
    id: StepIdentifier,
    isOptional: Boolean,
    title: String?,
    text: String?,
    nextButtonText: String,
    private val answerFormat: AnswerFormat.ScaleAnswerFormat,
    private val preselected: Float? = null
) : QuestionView(context, id, isOptional, title, text, nextButtonText) {

    //region Members

    private lateinit var scalePart: ScalePart

    //endregion

    //region Overrides

    override fun createResults(): QuestionResult =
        ScaleQuestionResult(
            id = id,
            startDate = startDate,
            answer = scalePart.selected,
            stringIdentifier = scalePart.selected.toString()
        )

    override fun isValidInput(): Boolean = true

    //endregion

    //region Private API

    override fun setupViews() {
        super.setupViews()

        val minimumValueDescription = answerFormat.minimumValueDescription
        val maximumValueDescription = answerFormat.maximumValueDescription

        val minValueDescription =
            if (minimumValueDescription.isNotBlank())
                minimumValueDescription
            else
                answerFormat.minimumValue.toString()
        val maxValueDescription =
            if (maximumValueDescription.isNotBlank())
                maximumValueDescription
            else
                answerFormat.maximumValue.toString()

        scalePart = content.add(
            ScalePart(
                context = context,
                minimumValue = answerFormat.minimumValue,
                minimumValueDescription = minValueDescription,
                maximumValue = answerFormat.maximumValue,
                maximumValueDescription = maxValueDescription,
                step = answerFormat.step,
                defaultValue = preselected ?: answerFormat.defaultValue.toFloat()
            )
        )
    }

    //endregion
}
