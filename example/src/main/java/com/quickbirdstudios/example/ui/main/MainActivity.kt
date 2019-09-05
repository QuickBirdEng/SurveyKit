package com.quickbirdstudios.example.ui.main

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.quickbirdstudios.example.R
import com.quickbirdstudios.surveykit.*
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.util.*
import kotlin.coroutines.CoroutineContext


class MainActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var survey: SurveyView

    override val coroutineContext: CoroutineContext = Job() + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        survey = findViewById(R.id.survey_view)
        setupSurvey(survey)
    }


    private fun setupSurvey(surveyView: SurveyView) {
        val steps = listOf(
            InstructionStep(
                title = R.string.intro_title,
                text = R.string.intro_text,
                buttonText = R.string.intro_start
            ),
            QuestionStep(
                title = R.string.about_you_question_title,
                text = R.string.about_you_question_text,
                answerFormat = AnswerFormat.TextAnswerFormat(
                    multipleLines = true,
                    maximumLength = 100
                )
            ),
            QuestionStep(
                title = R.string.how_old_title,
                text = R.string.how_old_text,
                answerFormat = AnswerFormat.IntegerAnswerFormat(
                    defaultValue = 25
                )
            ),
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
                title = R.string.physical_disabilities_question_title,
                text = R.string.physical_disabilities_question_text,
                answerFormat = AnswerFormat.MultipleChoiceAnswerFormat(
                    textChoices = listOf(
                        TextChoice(R.string.physical_disabilities_back_pain),
                        TextChoice(R.string.physical_disabilities_heart_problems),
                        TextChoice(R.string.physical_disabilities_joint_pain),
                        TextChoice(R.string.physical_disabilities_joint_asthma)
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
            CompletionStep(
                title = R.string.finish_question_title,
                text = R.string.finish_question_text,
                buttonText = R.string.finish_question_submit
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
                }
            }
        }

        val configuration = SurveyTheme(
            themeColorDark = ContextCompat.getColor(this, R.color.cyan_dark),
            themeColor = ContextCompat.getColor(this, R.color.cyan_normal),
            textColor = ContextCompat.getColor(this, R.color.cyan_text)
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

            val root = View.inflate(context, R.layout.example, this)

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
