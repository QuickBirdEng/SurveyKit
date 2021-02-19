package com.quickbirdstudios.surveykit.steps

import android.content.Context
import androidx.lifecycle.Lifecycle
import com.quickbirdstudios.surveykit.AnswerFormat
import com.quickbirdstudios.surveykit.AnswerFormat.*
import com.quickbirdstudios.surveykit.StepIdentifier
import com.quickbirdstudios.surveykit.backend.address.AddressSuggestionProvider
import com.quickbirdstudios.surveykit.backend.address.GeocoderAddressSuggestionProvider
import com.quickbirdstudios.surveykit.backend.views.questions.*
import com.quickbirdstudios.surveykit.backend.views.step.QuestionView
import com.quickbirdstudios.surveykit.result.QuestionResult
import com.quickbirdstudios.surveykit.result.StepResult
import com.quickbirdstudios.surveykit.result.question_results.*

class QuestionStep(
    val title: String,
    val text: String,
    val nextButtonText: String = "Next",
    val skipButtonText: String = "Skip",
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
            is LocationAnswerFormat -> createLocationPickerQuestion(context, stepResult)
        }

    //endregion

    //region Private API

    private fun createTextQuestion(context: Context, stepResult: StepResult?) =
        TextQuestionView(
            context = context,
            id = id,
            title = title,
            text = text,
            nextButtonText = nextButtonText,
            isOptional = isOptional,
            answerFormat = this.answerFormat as TextAnswerFormat,
            preselected = stepResult.toSpecificResult<TextQuestionResult>()?.answer,
            skipButtonText = skipButtonText
        )

    private fun createSingleChoiceQuestion(context: Context, stepResult: StepResult?) =
        SingleChoiceQuestionView(
            context = context,
            id = id,
            title = title,
            text = text,
            isOptional = isOptional,
            nextButtonText = nextButtonText,
            answerFormat = this.answerFormat as SingleChoiceAnswerFormat,
            preselected = stepResult.toSpecificResult<SingleChoiceQuestionResult>()?.answer,
            skipButtonText = skipButtonText
        )

    private fun createMultipleChoiceQuestion(context: Context, stepResult: StepResult?) =
        MultipleChoiceQuestionView(
            context = context,
            id = id,
            title = title,
            text = text,
            isOptional = isOptional,
            nextButtonText = nextButtonText,
            answerFormat = this.answerFormat as MultipleChoiceAnswerFormat,
            preselected = stepResult.toSpecificResult<MultipleChoiceQuestionResult>()?.answer,
            skipButtonText = skipButtonText
        )

    private fun createScaleQuestion(context: Context, stepResult: StepResult?) =
        ScaleQuestionView(
            context = context,
            id = id,
            title = title,
            text = text,
            isOptional = isOptional,
            nextButtonText = nextButtonText,
            answerFormat = this.answerFormat as ScaleAnswerFormat,
            preselected = stepResult.toSpecificResult<ScaleQuestionResult>()?.answer,
            skipButtonText = skipButtonText
        )

    private fun createIntegerQuestion(context: Context, stepResult: StepResult?) =
        IntegerQuestionView(
            context = context,
            id = id,
            title = title,
            text = text,
            isOptional = isOptional,
            nextButtonText = nextButtonText,
            answerFormat = this.answerFormat as IntegerAnswerFormat,
            preselected = stepResult.toSpecificResult<IntegerQuestionResult>()?.answer,
            skipButtonText = skipButtonText
        )

    private fun createBooleanQuestion(context: Context, stepResult: StepResult?) =
        BooleanQuestionView(
            context = context,
            id = id,
            title = title,
            text = text,
            isOptional = isOptional,
            nextButtonText = nextButtonText,
            answerFormat = this.answerFormat as BooleanAnswerFormat,
            preselected = stepResult.toSpecificResult<BooleanQuestionResult>()?.answer,
            skipButtonText = skipButtonText
        )

    private fun createValuePickerQuestion(context: Context, stepResult: StepResult?) =
        ValuePickerQuestionView(
            context = context,
            id = id,
            title = title,
            text = text,
            isOptional = isOptional,
            nextButtonText = nextButtonText,
            answerFormat = this.answerFormat as ValuePickerAnswerFormat,
            preselected = stepResult.toSpecificResult<ValuePickerQuestionResult>()?.answer,
            skipButtonText = skipButtonText
        )

    private fun createDatePickerQuestion(context: Context, stepResult: StepResult?) =
        DatePickerQuestionView(
            context = context,
            id = id,
            title = title,
            text = text,
            isOptional = isOptional,
            nextButtonText = nextButtonText,
            answerFormat = this.answerFormat as DateAnswerFormat,
            preselected = stepResult.toSpecificResult<DateQuestionResult>()?.answer,
            skipButtonText = skipButtonText
        )

    private fun createTimePickerQuestion(context: Context, stepResult: StepResult?) =
        TimePickerQuestionView(
            context = context,
            id = id,
            title = title,
            text = text,
            isOptional = isOptional,
            nextButtonText = nextButtonText,
            answerFormat = this.answerFormat as TimeAnswerFormat,
            preselected = stepResult.toSpecificResult<TimeQuestionResult>()?.answer,
            skipButtonText = skipButtonText
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
            nextButtonText = nextButtonText,
            answerFormat = this.answerFormat as DateTimeAnswerFormat,
            preselected = stepResult.toSpecificResult<DateTimeQuestionResult>()?.answer,
            skipButtonText = skipButtonText
        )

    private fun createEmailQuestion(context: Context, stepResult: StepResult?) =
        EmailQuestionView(
            context = context,
            id = id,
            title = title,
            text = text,
            isOptional = isOptional,
            nextButtonText = nextButtonText,
            answerFormat = this.answerFormat as EmailAnswerFormat,
            preselected = stepResult.toSpecificResult<EmailQuestionResult>()?.answer,
            skipButtonText = skipButtonText
        )

    private fun createImageSelectorQuestion(context: Context, stepResult: StepResult?) =
        ImageSelectorQuestionView(
            context = context,
            id = id,
            title = title,
            text = text,
            isOptional = isOptional,
            nextButtonText = nextButtonText,
            answerFormat = this.answerFormat as ImageSelectorFormat,
            preselected = stepResult.toSpecificResult<ImageSelectorResult>()?.answer,
            skipButtonText = skipButtonText
        )

    private fun createLocationPickerQuestion(
        context: Context,
        stepResult: StepResult?
    ): LocationPickerQuestionView {
        this.answerFormat as LocationAnswerFormat
        return LocationPickerQuestionView(
            context = context,
            id = id,
            title = title,
            text = text,
            isOptional = isOptional,
            nextButtonText = nextButton,
            lifecycle = answerFormat.lifecycle,
            addressProvider = answerFormat.addressProvider
                ?: GeocoderAddressSuggestionProvider(context),
            answerFormat = answerFormat,
            preselected = stepResult.toSpecificResult<LocationQuestionResult>()?.answer
        )
    }

//endregion

//region Private Helper

    @Suppress("UNCHECKED_CAST")
    private fun <R : QuestionResult> StepResult?.toSpecificResult(): R? =
        (this?.results?.firstOrNull() as? R)

//endregion
}
