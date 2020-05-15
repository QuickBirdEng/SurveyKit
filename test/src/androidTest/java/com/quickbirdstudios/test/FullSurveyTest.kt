package com.quickbirdstudios.test

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.quickbirdstudios.surveykit.AnswerFormat
import com.quickbirdstudios.surveykit.AnswerFormat.MultipleChoiceAnswerFormat
import com.quickbirdstudios.surveykit.AnswerFormat.SingleChoiceAnswerFormat
import com.quickbirdstudios.surveykit.FinishReason
import com.quickbirdstudios.surveykit.result.TaskResult
import com.quickbirdstudios.surveykit.result.question_results.BooleanQuestionResult
import com.quickbirdstudios.surveykit.result.question_results.DateQuestionResult
import com.quickbirdstudios.surveykit.result.question_results.EmailQuestionResult
import com.quickbirdstudios.surveykit.result.question_results.FinishQuestionResult
import com.quickbirdstudios.surveykit.result.question_results.ImageSelectorResult
import com.quickbirdstudios.surveykit.result.question_results.IntegerQuestionResult
import com.quickbirdstudios.surveykit.result.question_results.IntroQuestionResult
import com.quickbirdstudios.surveykit.result.question_results.MultipleChoiceQuestionResult
import com.quickbirdstudios.surveykit.result.question_results.SingleChoiceQuestionResult
import com.quickbirdstudios.surveykit.result.question_results.TextQuestionResult
import com.quickbirdstudios.surveykit.result.question_results.TimeQuestionResult
import com.quickbirdstudios.surveykit.result.question_results.ValuePickerQuestionResult
import com.quickbirdstudios.test.pages.PageTest
import com.quickbirdstudios.test.pages.testBooleanChoiceStep
import com.quickbirdstudios.test.pages.testCompletionStep
import com.quickbirdstudios.test.pages.testCustomStep
import com.quickbirdstudios.test.pages.testDatePickerStep
import com.quickbirdstudios.test.pages.testEmailStep
import com.quickbirdstudios.test.pages.testImageSelectorStep
import com.quickbirdstudios.test.pages.testIntroStep
import com.quickbirdstudios.test.pages.testMultipleChoiceStep
import com.quickbirdstudios.test.pages.testNumberStep
import com.quickbirdstudios.test.pages.testScaleStep
import com.quickbirdstudios.test.pages.testSingleChoiceStep
import com.quickbirdstudios.test.pages.testTextStep
import com.quickbirdstudios.test.pages.testTimePickerStep
import com.quickbirdstudios.test.pages.testValuePickerStep
import java.util.Calendar
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
internal class FullSurveyTest : PageTest {

    private val instrumentationRegistry = InstrumentationRegistry.getInstrumentation()
    private val context = instrumentationRegistry.targetContext
    private val inputMethodManager = context.getSystemService(
        Context.INPUT_METHOD_SERVICE
    ) as InputMethodManager

    @get:Rule
    var activityRule: ActivityTestRule<TestActivity> = ActivityTestRule(TestActivity::class.java)

    @Before
    fun setup() {
    }

    @Test
    fun runThrough() {
        activityRule.activity.survey.onSurveyFinish = resultCheck

        testIntroStep()

        testTextStep(text = TextStepInput) { isKeyboardShown() }

        testNumberStep(
            numberText = NumberStepInput,
            defaultValue =
            (activityRule.activity.intStep.answerFormat as AnswerFormat.IntegerAnswerFormat)
                .defaultValue
        ) { isKeyboardShown() }

        testScaleStep(progressToSetOn = 0, activity = activityRule.activity)

        testMultipleChoiceStep(context = context)

        testSingleChoiceStep(context = context)

        testBooleanChoiceStep()

        testValuePickerStep(ValuePickerStepInput)

        testDatePickerStep(
            year = DateStepInput.year,
            month = DateStepInput.month,
            day = DateStepInput.day
        )

        testTimePickerStep(hour = TimeStepInput.hour, minute = TimeStepInput.minute)

        testEmailStep(EmailStepInputWrong, EmailStepInputRight) { isKeyboardShown() }

        testImageSelectorStep(ImageSelectorStepToClick)

        testCustomStep()

        testCompletionStep()
    }

    //region Helper functions

    private fun isKeyboardShown(): Boolean {
        return inputMethodManager.isAcceptingText
    }

    //endregion

    //region Private Result checker

    private val resultCheck = { result: TaskResult, _: FinishReason ->
        result.results.forEach { stepResult ->
            stepResult.results.forEach { questionResult ->
                when (questionResult) {
                    is IntroQuestionResult -> Unit
                    is TextQuestionResult ->
                        Assert.assertTrue(questionResult.answer == TextStepInput)
                    is IntegerQuestionResult ->
                        Assert.assertTrue(questionResult.answer.toString() == NumberStepInput)
                    is MultipleChoiceQuestionResult -> {
                        val expectedResult = listOf(
                            (activityRule
                                .activity
                                .multipleChoiceStep
                                .answerFormat as MultipleChoiceAnswerFormat
                                ).textChoices[0],
                            (activityRule
                                .activity
                                .multipleChoiceStep
                                .answerFormat as MultipleChoiceAnswerFormat
                                ).textChoices[3]
                        )
                        Assert.assertArrayEquals(
                            expectedResult.toTypedArray(),
                            questionResult.answer.toTypedArray()
                        )
                    }
                    is SingleChoiceQuestionResult -> {
                        Assert.assertEquals(
                            (activityRule.activity.singleChoiceStep.answerFormat as SingleChoiceAnswerFormat)
                                .textChoices[0],
                            questionResult.answer
                        )
                    }
                    is BooleanQuestionResult -> Assert.assertEquals(
                        AnswerFormat.BooleanAnswerFormat.Result.PositiveAnswer,
                        questionResult.answer
                    )
                    is ValuePickerQuestionResult -> Assert.assertEquals(
                        ValuePickerStepInput.toString(),
                        questionResult.answer
                    )
                    is DateQuestionResult -> {
                        Assert.assertEquals(
                            DateStepInput,
                            questionResult.answer
                        )
                    }
                    is TimeQuestionResult -> Assert.assertEquals(
                        TimeStepInput,
                        questionResult.answer
                    )
                    is EmailQuestionResult -> Assert.assertEquals(
                        EmailStepInputRight,
                        questionResult.answer
                    )
                    is ImageSelectorResult -> Assert.assertEquals(
                        ImageSelectorStepToClick + ImageSelectorStepPreselected,
                        questionResult.answer
                    )
                    is CustomResult -> println(questionResult)
                    is FinishQuestionResult -> Unit
                }
            }
        }
    }

    //endregion

    companion object {
        private const val TextStepInput = "TextStepInput"
        private const val NumberStepInput = "35"
        private const val EmailStepInputWrong = "asdf@test"
        private const val EmailStepInputRight = "email@test.com"
        private val DateStepInput = AnswerFormat.DateAnswerFormat.Date(
            day = 1,
            month = 0,
            year = Calendar.getInstance()[Calendar.YEAR]
        )
        private val TimeStepInput = AnswerFormat.TimeAnswerFormat.Time(hour = 1, minute = 1)
        private const val ValuePickerStepInput = 0
        private val ImageSelectorStepPreselected = listOf(1, 3)
        private val ImageSelectorStepToClick = listOf(0)
    }
}
