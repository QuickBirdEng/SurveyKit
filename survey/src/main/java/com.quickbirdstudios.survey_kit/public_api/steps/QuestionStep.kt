package com.quickbirdstudios.survey_kit.public_api.steps

import android.content.Context
import androidx.annotation.StringRes
import com.quickbirdstudios.survey_kit.backend.views.questions.*
import com.quickbirdstudios.survey_kit.backend.views.step.QuestionView
import com.quickbirdstudios.survey_kit.public_api.*
import com.quickbirdstudios.survey_kit.public_api.result.StepResult
import com.quickbirdstudios.survey_kit.public_api.result.question_results.*
import com.quickbirdstudios.triangle.survey.R

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
        SingleChoiceQuestion(
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

    //endregion


}
