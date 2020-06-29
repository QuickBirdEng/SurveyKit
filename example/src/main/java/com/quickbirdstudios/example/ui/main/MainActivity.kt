package com.quickbirdstudios.example.ui.main

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.quickbirdstudios.example.R
import com.quickbirdstudios.surveykit.AnswerFormat
import com.quickbirdstudios.surveykit.FinishReason
import com.quickbirdstudios.surveykit.Identifier
import com.quickbirdstudios.surveykit.ImageChoice
import com.quickbirdstudios.surveykit.NavigableOrderedTask
import com.quickbirdstudios.surveykit.NavigationRule
import com.quickbirdstudios.surveykit.StepIdentifier
import com.quickbirdstudios.surveykit.SurveyTheme
import com.quickbirdstudios.surveykit.TextChoice
import com.quickbirdstudios.surveykit.backend.views.main_parts.AbortDialogConfiguration
import com.quickbirdstudios.surveykit.backend.views.step.StepView
import com.quickbirdstudios.surveykit.result.QuestionResult
import com.quickbirdstudios.surveykit.result.StepResult
import com.quickbirdstudios.surveykit.result.TaskResult
import com.quickbirdstudios.surveykit.steps.CompletionStep
import com.quickbirdstudios.surveykit.steps.InstructionStep
import com.quickbirdstudios.surveykit.steps.QuestionStep
import com.quickbirdstudios.surveykit.steps.Step
import com.quickbirdstudios.surveykit.survey.SurveyView
import java.util.Date
import kotlinx.android.parcel.Parcelize

class MainActivity : AppCompatActivity() {

    protected lateinit var survey: SurveyView
    private lateinit var container: ViewGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        survey = findViewById(R.id.survey_view)
        container = findViewById(R.id.surveyContainer)
        setupSurvey(survey)

    }

    private fun setupSurvey(surveyView: SurveyView) {
        val steps = listOf(
            InstructionStep(
                title = this.resources.getString(R.string.intro_title),
                text = this.resources.getString(R.string.intro_text),
                buttonText = this.resources.getString(R.string.intro_start)
            ),
            QuestionStep(
                title = this.resources.getString(R.string.about_you_question_title),
                text = this.resources.getString(R.string.about_you_question_text),
                answerFormat = AnswerFormat.TextAnswerFormat(maxLines = 5)
            ),
            QuestionStep(
                title = this.resources.getString(R.string.how_old_title),
                text = this.resources.getString(R.string.how_old_text),
                answerFormat = AnswerFormat.IntegerAnswerFormat(
                    defaultValue = 25,
                    hint = this.resources.getString(R.string.how_old_hint)
                )
            ),
            QuestionStep(
                title = this.resources.getString(R.string.how_fat_question_title),
                text = this.resources.getString(R.string.how_fat_question_text),
                answerFormat = AnswerFormat.ScaleAnswerFormat(
                    minimumValue = 1,
                    maximumValue = 5,
                    minimumValueDescription = this.resources.getString(R.string.how_fat_min),
                    maximumValueDescription = this.resources.getString(R.string.how_fat_max),
                    step = 1f,
                    defaultValue = 3
                )
            ),
            QuestionStep(
                title = this.resources.getString(R.string.allergies_question_title),
                text = this.resources.getString(R.string.allergies_question_text),
                answerFormat = AnswerFormat.MultipleChoiceAnswerFormat(
                    textChoices = listOf(
                        TextChoice(this.resources.getString(R.string.allergies_back_penicillin)),
                        TextChoice(this.resources.getString(R.string.allergies_latex)),
                        TextChoice(this.resources.getString(R.string.allergies_pet)),
                        TextChoice(this.resources.getString(R.string.allergies_pollen))
                    )
                )
            ),
            QuestionStep(
                title = this.resources.getString(R.string.quit_or_continue_question_title),
                text = this.resources.getString(R.string.quit_or_continue_question_text),
                answerFormat = AnswerFormat.SingleChoiceAnswerFormat(
                    textChoices = listOf(
                        TextChoice(this.resources.getString(R.string.yes)),
                        TextChoice(this.resources.getString(R.string.no))
                    )
                )
            ),
            CustomStep(),
            QuestionStep(
                title = this.resources.getString(R.string.boolean_example_title),
                text = this.resources.getString(R.string.boolean_example_text),
                answerFormat = AnswerFormat.BooleanAnswerFormat(
                    positiveAnswerText = this.resources.getString(R.string.how_fat_min),
                    negativeAnswerText = this.resources.getString(R.string.how_fat_max),
                    defaultValue = AnswerFormat.BooleanAnswerFormat.Result.NegativeAnswer
                )
            ),
            QuestionStep(
                title = this.resources.getString(R.string.value_picker_example_title),
                text = this.resources.getString(R.string.value_picker_example_text),
                answerFormat = AnswerFormat.ValuePickerAnswerFormat(
                    choices = (0..10).toList().map { it.toString() },
                    defaultValue = 5.toString()
                )
            ),
            QuestionStep(
                title = this.resources.getString(R.string.date_picker_title),
                text = this.resources.getString(R.string.date_picker_text),
                answerFormat = AnswerFormat.DateAnswerFormat()
            ),
            QuestionStep(
                title = this.resources.getString(R.string.time_picker_title),
                text = this.resources.getString(R.string.time_picker_text),
                answerFormat = AnswerFormat.TimeAnswerFormat()
            ),
            QuestionStep(
                title = this.resources.getString(R.string.email_question_title),
                text = this.resources.getString(R.string.email_question_text),
                answerFormat = AnswerFormat.EmailAnswerFormat()
            ),
            QuestionStep(
                title = this.resources.getString(R.string.image_selector_question_title),
                text = this.resources.getString(R.string.image_selector_question_text),
                answerFormat = AnswerFormat.ImageSelectorFormat(
                    numberOfColumns = 5,
                    defaultSelectedImagesIndices = listOf(1, 3),
                    imageChoiceList = listOf(
                        ImageChoice(R.drawable.color1),
                        ImageChoice(R.drawable.color2),
                        ImageChoice(R.drawable.color3),
                        ImageChoice(R.drawable.color4),
                        ImageChoice(R.drawable.color5),
                        ImageChoice(R.drawable.color6)
                    )
                )
            ),
            QuestionStep(
                title = getString(R.string.date_time_question_title),
                text = getString(R.string.date_time_question_text),
                answerFormat = AnswerFormat.DateTimeAnswerFormat()
            ),
            CompletionStep(
                title = this.resources.getString(R.string.finish_question_title),
                text = this.resources.getString(R.string.finish_question_text),
                buttonText = this.resources.getString(R.string.finish_question_submit)
            )
        )

        val task = NavigableOrderedTask(steps = steps)

        task.setNavigationRule(
            steps[5].id,
            NavigationRule.DirectStepNavigationRule(
                destinationStepStepIdentifier = steps[6].id
            )
        )

        task.setNavigationRule(
            steps[7].id,
            NavigationRule.ConditionalDirectionStepNavigationRule(
                resultToStepIdentifierMapper = { input ->
                    when (input) {
                        "Ja" -> steps[7].id
                        "Nein" -> steps[0].id
                        else -> null
                    }
                }
            )
        )

        surveyView.onSurveyFinish = { taskResult: TaskResult, reason: FinishReason ->
            if (reason == FinishReason.Completed) {
                taskResult.results.forEach { stepResult ->
                    Log.e("ASDF", "answer ${stepResult.results.firstOrNull()}")
                    container.removeAllViews()
                }
            }
        }


        val configuration = SurveyTheme(
            themeColorDark = ContextCompat.getColor(this, R.color.cyan_dark),
            themeColor = ContextCompat.getColor(this, R.color.cyan_normal),
            textColor = ContextCompat.getColor(this, R.color.cyan_text),
            abortDialogConfiguration = AbortDialogConfiguration(
                title = R.string.title,
                message = R.string.message,
                neutralMessage = R.string.no,
                negativeMessage = R.string.yes
            )
        )

        surveyView.start(task, configuration)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            survey.backPressed()
            true
        } else false
    }
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
                root.findViewById<Button>(R.id.close_button)
                    .setOnClickListener { onCloseListener(createResults(), FinishReason.Completed) }
                root.findViewById<Button>(R.id.skip_button)
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
