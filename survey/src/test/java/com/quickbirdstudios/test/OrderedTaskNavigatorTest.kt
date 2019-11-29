package com.quickbirdstudios.test

import com.quickbirdstudios.surveykit.AnswerFormat
import com.quickbirdstudios.surveykit.OrderedTask
import com.quickbirdstudios.surveykit.backend.navigator.TaskNavigator
import com.quickbirdstudios.surveykit.steps.QuestionStep
import org.junit.Assert
import org.junit.Test

internal class OrderedTaskNavigatorTest {

    @Test
    fun testStraightWalkThrough() {
        val firstQuestion = randomQuestionStep()
        val secondQuestion = randomQuestionStep()
        val thirdQuestion = randomQuestionStep()
        val orderedTask = OrderedTask(listOf(firstQuestion, secondQuestion, thirdQuestion))
        val navigator = TaskNavigator(orderedTask)

        Assert.assertEquals(firstQuestion, navigator.startStep(null))
        Assert.assertEquals(secondQuestion, navigator.nextStep(firstQuestion))
        Assert.assertEquals(thirdQuestion, navigator.nextStep(secondQuestion))
        Assert.assertNull(navigator.nextStep(thirdQuestion))
    }

    @Test
    fun testBackwards() {
        val firstQuestion = randomQuestionStep()
        val secondQuestion = randomQuestionStep()
        val thirdQuestion = randomQuestionStep()
        val orderedTask = OrderedTask(listOf(firstQuestion, secondQuestion, thirdQuestion))
        val navigator = TaskNavigator(orderedTask)

        Assert.assertEquals(secondQuestion, navigator.previousStep(thirdQuestion))
        Assert.assertEquals(firstQuestion, navigator.previousStep(secondQuestion))
        Assert.assertNull(navigator.previousStep(firstQuestion))
    }

    @Test
    fun testFinalStep() {
        val finalStep = randomQuestionStep()
        val orderedTask = OrderedTask((0..100).map { randomQuestionStep() } + finalStep)
        val navigator = TaskNavigator(orderedTask)

        Assert.assertEquals(finalStep, navigator.finalStep())
    }

    private fun randomQuestionStep() = QuestionStep(
        title = "title",
        text = "text",
        answerFormat = AnswerFormat.TextAnswerFormat(maxLines = 1)
    )
}
