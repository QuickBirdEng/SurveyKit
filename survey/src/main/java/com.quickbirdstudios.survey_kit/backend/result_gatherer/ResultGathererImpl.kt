package com.quickbirdstudios.survey_kit.backend.result_gatherer

import com.quickbirdstudios.survey_kit.StepIdentifier
import com.quickbirdstudios.survey_kit.Task
import com.quickbirdstudios.survey_kit.result.StepResult
import com.quickbirdstudios.survey_kit.result.TaskResult
import java.util.*

internal class ResultGathererImpl(private val task: Task) : ResultGatherer {

    override var results: MutableList<StepResult> = mutableListOf()

    override val taskResult: TaskResult
        get() = TaskResult(
            id = task.id,
            results = results,
            startDate = Date()
        ).apply {
            endDate = Date()
        }

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
