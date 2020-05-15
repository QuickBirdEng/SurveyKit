package com.quickbirdstudios.surveykit.backend.navigator

import com.quickbirdstudios.surveykit.OrderedTask
import com.quickbirdstudios.surveykit.result.StepResult
import com.quickbirdstudios.surveykit.steps.Step
import java.util.Stack

internal class OrderedTaskNavigator(
    override val task: OrderedTask
) : TaskNavigator {

    //region Public API

    override var history: Stack<Step> = Stack()

    override fun startStep(stepResult: StepResult?): Step? {
        val previousStep = peekHistory()
        return if (previousStep == null) task.steps.firstOrNull()
        else previousStep.nextInList()
    }

    override fun finalStep(): Step? = task.steps.lastOrNull()

    override fun nextStep(step: Step, stepResult: StepResult?): Step? = step.nextInList()

    override fun previousStep(step: Step): Step? = step.previousInList()

    //endregion

    //region Private Helper

    private fun Step.previousInList(): Step? {
        val currentStepIndex = task.steps.indexOf(this)
        return if (currentStepIndex - 1 < 0) null
        else task.steps[currentStepIndex - 1]
    }

    //endregion
}
