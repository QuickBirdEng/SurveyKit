package com.quickbirdstudios.survey_kit.backend.views.questions

import android.content.Context
import androidx.annotation.StringRes
import com.quickbirdstudios.survey_kit.AnswerFormat
import com.quickbirdstudios.survey_kit.StepIdentifier
import com.quickbirdstudios.survey_kit.backend.views.question_parts.ScalePart
import com.quickbirdstudios.survey_kit.backend.views.step.QuestionView
import com.quickbirdstudios.survey_kit.result.QuestionResult
import com.quickbirdstudios.survey_kit.result.question_results.ScaleQuestionResult

internal class ScaleQuestionView(
    context: Context,
    id: StepIdentifier,
    isOptional: Boolean,
    @StringRes title: Int?,
    @StringRes text: Int?,
    @StringRes nextButtonText: Int,
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

    override fun setState() {
        val scaleState = (state as? ScaleQuestionResult)?.answer ?: return
        scalePart.selected = scaleState
    }

    override fun isValidInput(): Boolean = true

    //endregion


    //region Private API

    override fun setupViews() {
        super.setupViews()

        val minimumValueDescription = context.getString(answerFormat.minimumValueDescription)
        val maximumValueDescription = context.getString(answerFormat.maximumValueDescription)

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
