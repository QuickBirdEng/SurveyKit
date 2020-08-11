package com.quickbirdstudios.surveykit.steps

import android.content.Context
import com.quickbirdstudios.surveykit.AnswerFormat
import com.quickbirdstudios.surveykit.AnswerFormat.BooleanAnswerFormat
import com.quickbirdstudios.surveykit.AnswerFormat.DateAnswerFormat
import com.quickbirdstudios.surveykit.AnswerFormat.DateTimeAnswerFormat
import com.quickbirdstudios.surveykit.AnswerFormat.DoubleAnswerFormat
import com.quickbirdstudios.surveykit.AnswerFormat.EmailAnswerFormat
import com.quickbirdstudios.surveykit.AnswerFormat.ImageSelectorFormat
import com.quickbirdstudios.surveykit.AnswerFormat.IntegerAnswerFormat
import com.quickbirdstudios.surveykit.AnswerFormat.MultipleChoiceAnswerFormat
import com.quickbirdstudios.surveykit.AnswerFormat.ScaleAnswerFormat
import com.quickbirdstudios.surveykit.AnswerFormat.SingleChoiceAnswerFormat
import com.quickbirdstudios.surveykit.AnswerFormat.TextAnswerFormat
import com.quickbirdstudios.surveykit.AnswerFormat.TimeAnswerFormat
import com.quickbirdstudios.surveykit.AnswerFormat.ValuePickerAnswerFormat
import com.quickbirdstudios.surveykit.StepIdentifier
import com.quickbirdstudios.surveykit.backend.views.questions.BooleanQuestionView
import com.quickbirdstudios.surveykit.backend.views.questions.DatePickerQuestionView
import com.quickbirdstudios.surveykit.backend.views.questions.DateTimePickerQuestionView
import com.quickbirdstudios.surveykit.backend.views.questions.DoubleQuestionView
import com.quickbirdstudios.surveykit.backend.views.questions.EmailQuestionView
import com.quickbirdstudios.surveykit.backend.views.questions.ImageSelectorQuestionView
import com.quickbirdstudios.surveykit.backend.views.questions.IntegerQuestionView
import com.quickbirdstudios.surveykit.backend.views.questions.MultipleChoiceQuestionView
import com.quickbirdstudios.surveykit.backend.views.questions.ScaleQuestionView
import com.quickbirdstudios.surveykit.backend.views.questions.SingleChoiceQuestionView
import com.quickbirdstudios.surveykit.backend.views.questions.TextQuestionView
import com.quickbirdstudios.surveykit.backend.views.questions.TimePickerQuestionView
import com.quickbirdstudios.surveykit.backend.views.questions.ValuePickerQuestionView
import com.quickbirdstudios.surveykit.backend.views.step.QuestionView
import com.quickbirdstudios.surveykit.result.QuestionResult
import com.quickbirdstudios.surveykit.result.StepResult
import com.quickbirdstudios.surveykit.result.question_results.BooleanQuestionResult
import com.quickbirdstudios.surveykit.result.question_results.DateQuestionResult
import com.quickbirdstudios.surveykit.result.question_results.DateTimeQuestionResult
import com.quickbirdstudios.surveykit.result.question_results.DoubleQuestionResult
import com.quickbirdstudios.surveykit.result.question_results.EmailQuestionResult
import com.quickbirdstudios.surveykit.result.question_results.ImageSelectorResult
import com.quickbirdstudios.surveykit.result.question_results.IntegerQuestionResult
import com.quickbirdstudios.surveykit.result.question_results.MultipleChoiceQuestionResult
import com.quickbirdstudios.surveykit.result.question_results.ScaleQuestionResult
import com.quickbirdstudios.surveykit.result.question_results.SingleChoiceQuestionResult
import com.quickbirdstudios.surveykit.result.question_results.TextQuestionResult
import com.quickbirdstudios.surveykit.result.question_results.TimeQuestionResult
import com.quickbirdstudios.surveykit.result.question_results.ValuePickerQuestionResult

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
            is DoubleAnswerFormat -> createDoubleQuestion(context, stepResult)
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

    private fun createDoubleQuestion(context: Context, stepResult: StepResult?) =
        DoubleQuestionView(
            context = context,
            id = id,
            title = title,
            text = text,
            isOptional = isOptional,
            nextButtonText = nextButton,
            answerFormat = this.answerFormat as DoubleAnswerFormat,
            preselected = stepResult.toSpecificResult<DoubleQuestionResult>()?.answer
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
