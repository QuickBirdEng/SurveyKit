package com.quickbirdstudios.survey_kit.backend.navigator

import com.quickbirdstudios.survey_kit.public_api.NavigableOrderedTask
import com.quickbirdstudios.survey_kit.public_api.OrderedTask
import com.quickbirdstudios.survey_kit.public_api.Task
import com.quickbirdstudios.survey_kit.public_api.result.StepResult
import com.quickbirdstudios.survey_kit.public_api.steps.Step
import java.util.*

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
                is OrderedTask -> OrderedTaskNavigator(task)
                is NavigableOrderedTask -> NavigableOrderedTaskNavigator(task)
                else -> throw NotImplementedError()
            }
    }
}

