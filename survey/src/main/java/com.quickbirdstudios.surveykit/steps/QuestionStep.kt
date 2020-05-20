package com.quickbirdstudios.surveykit.steps

import android.content.Context
import com.quickbirdstudios.surveykit.AnswerFormat
import com.quickbirdstudios.surveykit.AnswerFormat.*
import com.quickbirdstudios.surveykit.StepIdentifier
import com.quickbirdstudios.surveykit.backend.views.questions.*
import com.quickbirdstudios.surveykit.backend.views.step.QuestionView
import com.quickbirdstudios.surveykit.result.QuestionResult
import com.quickbirdstudios.surveykit.result.StepResult
import com.quickbirdstudios.surveykit.result.question_results.*

class QuestionStep(
    val title: String,
    val text: String,
    val nextButton: String = "Next",
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
            is DateTimeAnswerFormat -> createDateTimePickerQuestion(context, stepResult)
            is EmailAnswerFormat -> createEmailQuestion(context, stepResult)
            is ImageSelectorFormat -> createImageSelectorQuestion(context, stepResult)
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
            answerFormat = this.answerFormat as TextAnswerFormat,
            preselected = stepResult.toSpecificResult<TextQuestionResult>()?.answer
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
            preselected = stepResult.toSpecificResult<SingleChoiceQuestionResult>()?.answer
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
            preselected = stepResult.toSpecificResult<MultipleChoiceQuestionResult>()?.answer
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
            preselected = stepResult.toSpecificResult<ScaleQuestionResult>()?.answer
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
            preselected = stepResult.toSpecificResult<IntegerQuestionResult>()?.answer
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
            preselected = stepResult.toSpecificResult<BooleanQuestionResult>()?.answer
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
            preselected = stepResult.toSpecificResult<ValuePickerQuestionResult>()?.answer
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
            preselected = stepResult.toSpecificResult<DateQuestionResult>()?.answer
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
            preselected = stepResult.toSpecificResult<TimeQuestionResult>()?.answer
        )

    private fun createDateTimePickerQuestion(
        context: Context,
        stepResult: StepResult?
    ): QuestionView =
        DateTimePickerQuestionView(
            context = context,
            id = id,
            title = title,
            text = text,
            isOptional = isOptional,
            nextButtonText = nextButton,
            answerFormat = this.answerFormat as DateTimeAnswerFormat,
            preselected = stepResult.toSpecificResult<DateTimeQuestionResult>()?.answer
        )

    private fun createEmailQuestion(context: Context, stepResult: StepResult?) =
        EmailQuestionView(
            context = context,
            id = id,
            title = title,
            text = text,
            isOptional = isOptional,
            nextButtonText = nextButton,
            answerFormat = this.answerFormat as EmailAnswerFormat,
            preselected = stepResult.toSpecificResult<EmailQuestionResult>()?.answer
        )

    private fun createImageSelectorQuestion(context: Context, stepResult: StepResult?) =
        ImageSelectorQuestionView(
            context = context,
            id = id,
            title = title,
            text = text,
            isOptional = isOptional,
            nextButtonText = nextButton,
            answerFormat = this.answerFormat as ImageSelectorFormat,
            preselected = stepResult.toSpecificResult<ImageSelectorResult>()?.answer
        )

    //endregion

    //region Private Helper

    @Suppress("UNCHECKED_CAST")
    private fun <R : QuestionResult> StepResult?.toSpecificResult(): R? =
        (this?.results?.firstOrNull() as? R)

    //endregion
}
