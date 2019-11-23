package com.quickbirdstudios.surveykit

import androidx.test.rule.ActivityTestRule
import com.quickbirdstudios.example.R
import com.quickbirdstudios.example.ui.main.CustomStep
import com.quickbirdstudios.example.ui.main.MainActivity
import com.quickbirdstudios.surveykit.steps.CompletionStep
import com.quickbirdstudios.surveykit.steps.InstructionStep
import com.quickbirdstudios.surveykit.steps.QuestionStep

class SurveyKitTestApp : ActivityTestRule<MainActivity>(MainActivity::class.java) {

    init {
    }

    private val introStep = InstructionStep(
        title = R.string.intro_title,
        text = R.string.intro_text,
        buttonText = R.string.intro_start
    )
    private val textStep = QuestionStep(
        title = R.string.about_you_question_title,
        text = R.string.about_you_question_text,
        answerFormat = AnswerFormat.TextAnswerFormat(
            maxLines = 5
        )
    )
    private val intStep = QuestionStep(
        title = R.string.how_old_title,
        text = R.string.how_old_text,
        answerFormat = AnswerFormat.IntegerAnswerFormat(
            defaultValue = 25,
            hint = R.string.how_old_hint
        )
    )


    private val allSteps = listOf(
        introStep,
        textStep,
        intStep,
        QuestionStep(
            title = R.string.how_fat_question_title,
            text = R.string.how_fat_question_text,
            answerFormat = AnswerFormat.ScaleAnswerFormat(
                minimumValue = 1,
                maximumValue = 5,
                minimumValueDescription = R.string.how_fat_min,
                maximumValueDescription = R.string.how_fat_max,
                step = 1f,
                defaultValue = 3
            )
        ),
        QuestionStep(
            title = R.string.allergies_question_title,
            text = R.string.allergies_question_text,
            answerFormat = AnswerFormat.MultipleChoiceAnswerFormat(
                textChoices = listOf(
                    TextChoice(R.string.allergies_back_penicillin),
                    TextChoice(R.string.allergies_latex),
                    TextChoice(R.string.allergies_pet),
                    TextChoice(R.string.allergies_pollen)
                )
            )
        ),
        QuestionStep(
            title = R.string.quit_or_continue_question_title,
            text = R.string.quit_or_continue_question_text,
            answerFormat = AnswerFormat.SingleChoiceAnswerFormat(
                textChoices = listOf(
                    TextChoice(R.string.yes),
                    TextChoice(R.string.no)
                )
            )
        ),
        CustomStep(),
        QuestionStep(
            title = R.string.boolean_example_title,
            text = R.string.boolean_example_text,
            answerFormat = AnswerFormat.BooleanAnswerFormat(
                positiveAnswerText = R.string.how_fat_min,
                negativeAnswerText = R.string.how_fat_max,
                defaultValue = AnswerFormat.BooleanAnswerFormat.Result.NegativeAnswer
            )
        ),
        QuestionStep(
            title = R.string.value_picker_example_title,
            text = R.string.value_picker_example_text,
            answerFormat = AnswerFormat.ValuePickerAnswerFormat(
                choices = (0..10).toList().map { it.toString() }
                ,
                defaultValue = 5.toString()
            )
        ),
        QuestionStep(
            title = R.string.date_picker_title,
            text = R.string.date_picker_text,
            answerFormat = AnswerFormat.DateAnswerFormat()
        ),
        QuestionStep(
            title = R.string.time_picker_title,
            text = R.string.time_picker_text,
            answerFormat = AnswerFormat.TimeAnswerFormat()
        ),
        QuestionStep(
            title = R.string.email_question_title,
            text = R.string.email_question_text,
            answerFormat = AnswerFormat.EmailAnswerFormat()
        ),
        QuestionStep(
            title = R.string.image_selector_question_title,
            text = R.string.image_selector_question_text,
            answerFormat = AnswerFormat.ImageSelectorFormat(
                numberOfColumns = 5,
                defaultSelectedImagesIndices = listOf(1, 3),
                imageChoiceList = listOf(
                    ImageChoice(R.drawable.example2),
                    ImageChoice(R.drawable.example2),
                    ImageChoice(R.drawable.example2),
                    ImageChoice(R.drawable.example2),
                    ImageChoice(R.drawable.example2),
                    ImageChoice(R.drawable.example3),
                    ImageChoice(R.drawable.example3),
                    ImageChoice(R.drawable.example3),
                    ImageChoice(R.drawable.example3),
                    ImageChoice(R.drawable.example2),
                    ImageChoice(R.drawable.example2),
                    ImageChoice(R.drawable.example2),
                    ImageChoice(R.drawable.example2),
                    ImageChoice(R.drawable.example2),
                    ImageChoice(R.drawable.example3),
                    ImageChoice(R.drawable.example3),
                    ImageChoice(R.drawable.example3),
                    ImageChoice(R.drawable.example3)
                )
            )
        ),
        CompletionStep(
            title = R.string.finish_question_title,
            text = R.string.finish_question_text,
            buttonText = R.string.finish_question_submit
        )
    )
}
