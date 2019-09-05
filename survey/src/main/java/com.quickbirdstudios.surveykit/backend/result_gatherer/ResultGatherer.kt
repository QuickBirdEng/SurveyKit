package com.quickbirdstudios.surveykit.backend.result_gatherer

import com.quickbirdstudios.surveykit.StepIdentifier
import com.quickbirdstudios.surveykit.result.StepResult
import com.quickbirdstudios.surveykit.result.TaskResult

interface ResultGatherer {
    val taskResult: TaskResult
    var results: MutableList<StepResult>

    fun store(stepResult: StepResult)
    fun retrieve(identifier: StepIdentifier): StepResult?
}
