package com.quickbirdstudios.surveykit

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.quickbirdstudios.surveykit.backend.views.step.StepView
import com.quickbirdstudios.surveykit.result.QuestionResult
import com.quickbirdstudios.surveykit.result.StepResult
import com.quickbirdstudios.surveykit.result.TaskResult
import com.quickbirdstudios.surveykit.steps.CompletionStep
import com.quickbirdstudios.surveykit.steps.InstructionStep
import com.quickbirdstudios.surveykit.steps.QuestionStep
import com.quickbirdstudios.surveykit.steps.Step
import com.quickbirdstudios.surveykit.survey.SurveyView
import kotlinx.android.parcel.Parcelize
import java.util.*

internal class TestActivity : AppCompatActivity() {

    private lateinit var survey: SurveyView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        survey = findViewById(R.id.survey_view)
        setupSurvey(survey)
    }

    private fun setupSurvey(surveyView: SurveyView) {
        val task = NavigableOrderedTask(steps = allSteps)

        surveyView.onSurveyFinish = { taskResult: TaskResult, reason: FinishReason ->
            if (reason == FinishReason.Completed) {
                taskResult.results.forEach { stepResult ->
                    Log.e("ASDF", "answer ${stepResult.results.firstOrNull()}")
                }
            }
        }

        val configuration = SurveyTheme(
            themeColorDark = ContextCompat.getColor(this, R.color.cyan_normal),
            themeColor = ContextCompat.getColor(this, R.color.cyan_normal),
            textColor = ContextCompat.getColor(this, R.color.cyan_text)
        )

        surveyView.start(task, configuration)
    }

    val introStep = InstructionStep(
        title = R.string.intro_title,
        text = R.string.intro_text,
        buttonText = R.string.intro_start
    )
    val textStep = QuestionStep(
        title = R.string.about_you_question_title,
        text = R.string.about_you_question_text,
        answerFormat = AnswerFormat.TextAnswerFormat(
            maxLines = 5
        )
    )
    val intStep = QuestionStep(
        title = R.string.how_old_title,
        text = R.string.how_old_text,
        answerFormat = AnswerFormat.IntegerAnswerFormat(
            defaultValue = 25,
            hint = R.string.how_old_hint
        )
    )
    val scaleStep = QuestionStep(
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
    )
    val multipleChoiceStep = QuestionStep(
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
    )
    val singleChoiceStep = QuestionStep(
        title = R.string.quit_or_continue_question_title,
        text = R.string.quit_or_continue_question_text,
        answerFormat = AnswerFormat.SingleChoiceAnswerFormat(
            textChoices = listOf(
                TextChoice(R.string.yes),
                TextChoice(R.string.no)
            )
        )
    )
    val booleanChoiceStep = QuestionStep(
        title = R.string.boolean_example_title,
        text = R.string.boolean_example_text,
        answerFormat = AnswerFormat.BooleanAnswerFormat(
            positiveAnswerText = R.string.yes,
            negativeAnswerText = R.string.no,
            defaultValue = AnswerFormat.BooleanAnswerFormat.Result.NegativeAnswer
        )
    )
    val valuePickerStep = QuestionStep(
        title = R.string.value_picker_example_title,
        text = R.string.value_picker_example_text,
        answerFormat = AnswerFormat.ValuePickerAnswerFormat(
            choices = (0..10).toList().map { it.toString() },
            defaultValue = 5.toString()
        )
    )
    val datePickerStep = QuestionStep(
        title = R.string.date_picker_title,
        text = R.string.date_picker_text,
        answerFormat = AnswerFormat.DateAnswerFormat()
    )
    val timePickerStep = QuestionStep(
        title = R.string.time_picker_title,
        text = R.string.time_picker_text,
        answerFormat = AnswerFormat.TimeAnswerFormat()
    )
    val emailStep = QuestionStep(
        title = R.string.email_question_title,
        text = R.string.email_question_text,
        answerFormat = AnswerFormat.EmailAnswerFormat()
    )
    val imageSelectorStep = QuestionStep(
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
    )
    val completionStep = CompletionStep(
        title = R.string.finish_question_title,
        text = R.string.finish_question_text,
        buttonText = R.string.finish_question_submit
    )
    val customStep = CustomStep()

    val allSteps = listOf(
        introStep,
        textStep,
        intStep,
        scaleStep,
        multipleChoiceStep,
        singleChoiceStep,
        booleanChoiceStep,
        valuePickerStep,
        datePickerStep,
        timePickerStep,
        emailStep,
        imageSelectorStep,
        customStep,
        completionStep
    )

}


class CustomStep : Step {
    override val isOptional: Boolean = true
    override val id: StepIdentifier =
        StepIdentifier()
    val tmp = id

    override fun createView(context: Context, stepResult: StepResult?): StepView {
        return object : StepView(context, id, isOptional) {

            override fun setupViews() = Unit

            val root = View.inflate(context, R.layout.custom_step, this)

            override fun createResults(): QuestionResult =
                CustomResult(
                    root.findViewById<EditText>(R.id.input).text.toString(),
                    "stringIdentifier",
                    id,
                    Date(),
                    Date()
                )

            override fun isValidInput(): Boolean = this@CustomStep.isOptional

            override var isOptional: Boolean = this@CustomStep.isOptional
            override val id: StepIdentifier = tmp

            override fun style(surveyTheme: SurveyTheme) {
                // do styling here
            }

            init {
                root.findViewById<Button>(R.id.continue_button)
                    .setOnClickListener { onNextListener(createResults()) }
                root.findViewById<Button>(R.id.back_button)
                    .setOnClickListener { onBackListener(createResults()) }
                root.findViewById<Button>(R.id.close)
                    .setOnClickListener { onCloseListener(createResults(), FinishReason.Completed) }
                root.findViewById<Button>(R.id.skip)
                    .setOnClickListener { onSkipListener() }
                root.findViewById<EditText>(R.id.input).setText(
                    (stepResult?.results?.firstOrNull() as? CustomResult)?.customData ?: ""
                )
            }
        }
    }
}


@Parcelize
data class CustomResult(
    val customData: String,
    override val stringIdentifier: String,
    override val id: Identifier,
    override val startDate: Date,
    override var endDate: Date
) : QuestionResult, Parcelable
