package com.quickbirdstudios.surveykit.backend.navigator

import com.quickbirdstudios.surveykit.NavigableOrderedTask
import com.quickbirdstudios.surveykit.OrderedTask
import com.quickbirdstudios.surveykit.Task
import com.quickbirdstudios.surveykit.result.StepResult
import com.quickbirdstudios.surveykit.steps.Step
import java.util.Stack

interface TaskNavigator {

    val task: Task

    var history: Stack<Step>

    fun lastStepInHistory(): Step? = peekHistory()

    fun startStep(stepResult: StepResult?): Step?

    fun finalStep(): Step?

    fun nextStep(step: Step, stepResult: StepResult? = null): Step?

    fun previousStep(step: Step): Step?

    fun Step.nextInList(): Step? {
        val currentStepIndex = task.steps.indexOf(this)
        return if (currentStepIndex + 1 > task.steps.size - 1) null
        else task.steps[currentStepIndex + 1]
    }

    fun peekHistory(): Step? {
        if (history.isEmpty()) return null
        return history.peek()
    }

    companion object {
        operator fun invoke(task: Task): TaskNavigator =
            when (task) {
                is OrderedTask -> OrderedTaskNavigator(
                    task
                )
                is NavigableOrderedTask -> NavigableOrderedTaskNavigator(
                    task
                )
                else -> throw NotImplementedError()
            }
    }
}
