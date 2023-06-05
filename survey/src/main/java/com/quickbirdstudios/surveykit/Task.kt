package com.quickbirdstudios.surveykit

import com.quickbirdstudios.surveykit.steps.Step

interface Task {
    val id: TaskIdentifier
    val steps: List<Step>

    operator fun get(id: StepIdentifier) = steps.find { it.id == id }
}

open class OrderedTask(
    override val steps: List<Step>,
    override val id: TaskIdentifier = TaskIdentifier()
) : Task

class NavigableOrderedTask(
    override val steps: List<Step>,
    override val id: TaskIdentifier = TaskIdentifier()
) : Task {

    private val stepIdentifierToNavigationRuleMap: MutableMap<StepIdentifier, NavigationRule> =
        mutableMapOf()

    fun setNavigationRule(
        forTriggerStepStepIdentifier: StepIdentifier,
        navigationRule: NavigationRule
    ) {
        stepIdentifierToNavigationRuleMap[forTriggerStepStepIdentifier] = navigationRule
    }

    internal fun getRule(id: StepIdentifier): NavigationRule? =
        stepIdentifierToNavigationRuleMap[id]
}
