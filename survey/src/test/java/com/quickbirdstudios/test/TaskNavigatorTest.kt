package com.quickbirdstudios.test

import com.quickbirdstudios.surveykit.AnswerFormat
import com.quickbirdstudios.surveykit.NavigableOrderedTask
import com.quickbirdstudios.surveykit.OrderedTask
import com.quickbirdstudios.surveykit.backend.navigator.NavigableOrderedTaskNavigator
import com.quickbirdstudios.surveykit.backend.navigator.OrderedTaskNavigator
import com.quickbirdstudios.surveykit.backend.navigator.TaskNavigator
import com.quickbirdstudios.surveykit.steps.QuestionStep
import org.junit.Assert
import org.junit.Test

class TaskNavigatorTest {
    @Test
    fun testHasNoPreviousStep() {
        val firstQuestion = randomQuestionStep()
        val secondQuestion = randomQuestionStep()
        val thirdQuestion = randomQuestionStep()
        val taskNavigator = createTaskImplementations(listOf(firstQuestion, secondQuestion, thirdQuestion))

        for (nav in taskNavigator) {
            nav.startStep(null)
            Assert.assertFalse(nav.hasPreviousStep())
        }
    }

    @Test
    fun testHasPreviousStep() {
        val firstQuestion = randomQuestionStep()
        val secondQuestion = randomQuestionStep()
        val thirdQuestion = randomQuestionStep()
        val taskNavigator = createTaskImplementations(listOf(firstQuestion, secondQuestion, thirdQuestion))

        for (nav in taskNavigator) {
            nav.startStep(null)
            nav.nextStep(thirdQuestion)
            Assert.assertTrue(
                nav.hasPreviousStep()
            )
        }
    }

    private fun createTaskImplementations(steps: List<QuestionStep>): List<TaskNavigator> {
        return listOf(
            OrderedTaskNavigator(
                OrderedTask(steps)
            ),
            NavigableOrderedTaskNavigator(
                NavigableOrderedTask(steps)
            )
        )
    }

    private fun randomQuestionStep() = QuestionStep(
        title = "title",
        text = "text",
        answerFormat = AnswerFormat.TextAnswerFormat(maxLines = 1)
    )
}
