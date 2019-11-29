package com.quickbirdstudios.test

import com.quickbirdstudios.surveykit.AnswerFormat
import com.quickbirdstudios.surveykit.NavigableOrderedTask
import com.quickbirdstudios.surveykit.NavigationRule
import com.quickbirdstudios.surveykit.backend.navigator.TaskNavigator
import com.quickbirdstudios.surveykit.steps.QuestionStep
import org.junit.Assert
import org.junit.Test

internal class NavigableOrderedTaskNavigatorTest {

    @Test
    fun testStraightWalkThrough() {
        val firstQuestion = randomQuestionStep()
        val secondQuestion = randomQuestionStep()
        val thirdQuestion = randomQuestionStep()
        val forthQuestion = randomQuestionStep()
        val navigableOrderedTask = NavigableOrderedTask(
            listOf(firstQuestion, secondQuestion, thirdQuestion, forthQuestion)
        )
        navigableOrderedTask.setNavigationRule(
            firstQuestion.id, NavigationRule.DirectStepNavigationRule(secondQuestion.id)
        )
        navigableOrderedTask.setNavigationRule(
            secondQuestion.id, NavigationRule.DirectStepNavigationRule(thirdQuestion.id)
        )
        navigableOrderedTask.setNavigationRule(
            thirdQuestion.id, NavigationRule.DirectStepNavigationRule(forthQuestion.id)
        )
        val navigator = TaskNavigator(navigableOrderedTask)

        Assert.assertEquals(firstQuestion, navigator.startStep(null))
        Assert.assertEquals(secondQuestion, navigator.nextStep(firstQuestion))
        Assert.assertEquals(thirdQuestion, navigator.nextStep(secondQuestion))
        Assert.assertEquals(forthQuestion, navigator.nextStep(thirdQuestion))
        Assert.assertNull(navigator.nextStep(forthQuestion))
    }

    @Test
    fun testDifferentWalkingPath() {
        val firstQuestion = randomQuestionStep()
        val secondQuestion = randomQuestionStep()
        val thirdQuestion = randomQuestionStep()
        val forthQuestion = randomQuestionStep()
        val navigableOrderedTask = NavigableOrderedTask(
            listOf(firstQuestion, secondQuestion, thirdQuestion, forthQuestion)
        )
        navigableOrderedTask.setNavigationRule(
            firstQuestion.id, NavigationRule.DirectStepNavigationRule(forthQuestion.id)
        )
        navigableOrderedTask.setNavigationRule(
            forthQuestion.id, NavigationRule.DirectStepNavigationRule(thirdQuestion.id)
        )
        navigableOrderedTask.setNavigationRule(
            thirdQuestion.id, NavigationRule.DirectStepNavigationRule(secondQuestion.id)
        )
        val navigator = TaskNavigator(navigableOrderedTask)

        Assert.assertEquals(firstQuestion, navigator.startStep(null))
        Assert.assertEquals(forthQuestion, navigator.nextStep(firstQuestion))
        Assert.assertEquals(thirdQuestion, navigator.nextStep(forthQuestion))
        Assert.assertEquals(secondQuestion, navigator.nextStep(thirdQuestion))
        Assert.assertEquals(thirdQuestion, navigator.nextStep(secondQuestion))
    }

    private fun randomQuestionStep() = QuestionStep(
        title = "title",
        text = "text",
        answerFormat = AnswerFormat.TextAnswerFormat(maxLines = 1)
    )
}
