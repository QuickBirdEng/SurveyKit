package com.quickbirdstudios.surveykit.steps

import android.content.Context
import androidx.annotation.StringRes
import com.quickbirdstudios.survey.R
import com.quickbirdstudios.surveykit.AnswerFormat
import com.quickbirdstudios.surveykit.AnswerFormat.*
import com.quickbirdstudios.surveykit.StepIdentifier
import com.quickbirdstudios.surveykit.backend.views.questions.*
import com.quickbirdstudios.surveykit.backend.views.step.QuestionView
import com.quickbirdstudios.surveykit.result.StepResult
import com.quickbirdstudios.surveykit.result.question_results.*

class QuestionStep(
    @StringRes val title: Int,
    @StringRes val text: Int,
    @StringRes val nextButton: Int = R.string.next,
    val answerFormat: AnswerFormat,
    override var isOptional: Boolean = false,
    override val id: StepIdentifier = StepIdentifier()
) : Step {


    //region Public API

    override fun createView(context: Context, stepResult: StepResult?): QuestionView =
        when (answerFormat) {
            is TextAnswerFormat -> createTextQuestion(context, stepResult)
            is SingleChoiceAnswerFormat -> createSingleChoiceQuestion(context, stepResult)
            is MultipleChoiceAnswerFormat -> createMultipleChoiceQuestion(context, stepResult)
            is ScaleAnswerFormat -> createScaleQuestion(context, stepResult)
            is IntegerAnswerFormat -> createIntegerQuestion(context, stepResult)
            is BooleanAnswerFormat -> createBooleanQuestion(context, stepResult)
            is ValuePickerAnswerFormat -> createValuePickerQuestion(context, stepResult)
            is DateAnswerFormat -> createDatePickerQuestion(context, stepResult)
            is TimeAnswerFormat -> createTimePickerQuestion(context, stepResult)
        }

    //endregion


    //region Private API

    private fun createTextQuestion(context: Context, stepResult: StepResult?) =
        TextQuestionView(
            context = context,
            id = id,
            title = title,
            text = text,
            nextButtonText = nextButton,
            isOptional = isOptional,
            preselected = (stepResult?.results?.firstOrNull() as? TextQuestionResult)?.answer
        )

    private fun createSingleChoiceQuestion(context: Context, stepResult: StepResult?) =
        SingleChoiceQuestionView(
            context = context,
            id = id,
            title = title,
            text = text,
            isOptional = isOptional,
            nextButtonText = nextButton,
            answerFormat = this.answerFormat as SingleChoiceAnswerFormat,
            preselected = (stepResult?.results?.firstOrNull() as? SingleChoiceQuestionResult)?.answer
        )

    private fun createMultipleChoiceQuestion(context: Context, stepResult: StepResult?) =
        MultipleChoiceQuestionView(
            context = context,
            id = id,
            title = title,
            text = text,
            isOptional = isOptional,
            nextButtonText = nextButton,
            answerFormat = this.answerFormat as MultipleChoiceAnswerFormat,
            preselected = (stepResult?.results?.firstOrNull() as? MultipleChoiceQuestionResult)?.answer
        )

    private fun createScaleQuestion(context: Context, stepResult: StepResult?) =
        ScaleQuestionView(
            context = context,
            id = id,
            title = title,
            text = text,
            isOptional = isOptional,
            nextButtonText = nextButton,
            answerFormat = this.answerFormat as ScaleAnswerFormat,
            preselected = (stepResult?.results?.firstOrNull() as? ScaleQuestionResult)?.answer
        )

    private fun createIntegerQuestion(context: Context, stepResult: StepResult?) =
        IntegerQuestionView(
            context = context,
            id = id,
            title = title,
            text = text,
            isOptional = isOptional,
            nextButtonText = nextButton,
            answerFormat = this.answerFormat as IntegerAnswerFormat,
            preselected = (stepResult?.results?.firstOrNull() as? IntegerQuestionResult)?.answer
        )

    private fun createBooleanQuestion(context: Context, stepResult: StepResult?) =
        BooleanQuestionView(
            context = context,
            id = id,
            title = title,
            text = text,
            isOptional = isOptional,
            nextButtonText = nextButton,
            answerFormat = this.answerFormat as BooleanAnswerFormat,
            preselected = (stepResult?.results?.firstOrNull() as? BooleanQuestionResult)?.answer
        )

    private fun createValuePickerQuestion(context: Context, stepResult: StepResult?) =
        ValuePickerQuestionView(
            context = context,
            id = id,
            title = title,
            text = text,
            isOptional = isOptional,
            nextButtonText = nextButton,
            answerFormat = this.answerFormat as ValuePickerAnswerFormat,
            preselected = (stepResult?.results?.firstOrNull() as? ValuePickerQuestionResult)?.answer
        )

    private fun createDatePickerQuestion(context: Context, stepResult: StepResult?) =
        DatePickerQuestionView(
            context = context,
            id = id,
            title = title,
            text = text,
            isOptional = isOptional,
            nextButtonText = nextButton,
            answerFormat = this.answerFormat as DateAnswerFormat,
            preselected = (stepResult?.results?.firstOrNull() as? DateQuestionResult)?.answer
        )

    private fun createTimePickerQuestion(context: Context, stepResult: StepResult?) =
        TimePickerQuestionView(
            context = context,
            id = id,
            title = title,
            text = text,
            isOptional = isOptional,
            nextButtonText = nextButton,
            answerFormat = this.answerFormat as TimeAnswerFormat,
            preselected = (stepResult?.results?.firstOrNull() as? TimeQuestionResult)?.answer
        )

    //endregion


}
