package com.quickbirdstudios.surveykit.backend.result_gatherer

import com.quickbirdstudios.surveykit.StepIdentifier
import com.quickbirdstudios.surveykit.Task
import com.quickbirdstudios.surveykit.result.StepResult
import com.quickbirdstudios.surveykit.result.TaskResult
import java.util.Date

internal class ResultGathererImpl(private val task: Task) : ResultGatherer {

    private val startDate: Date = Date()

    override var results: MutableList<StepResult> = mutableListOf()

    override val taskResult: TaskResult
        get() = TaskResult(
            id = task.id,
            results = results,
            startDate = startDate
        ).apply { endDate = Date() }

    override fun store(stepResult: StepResult) {
        val previousResult = retrieve(stepResult.id)
        if (previousResult == null) results.add(stepResult)
        else {
            val previousResultIndex = results.indexOf(previousResult)
            results[previousResultIndex] = stepResult
        }
    }

    override fun retrieve(identifier: StepIdentifier) = results.find { it.id == identifier }
}
