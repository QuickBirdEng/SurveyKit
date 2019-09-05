package com.quickbirdstudios.survey_kit.backend.result_gatherer

import com.quickbirdstudios.survey_kit.StepIdentifier
import com.quickbirdstudios.survey_kit.result.StepResult
import com.quickbirdstudios.survey_kit.result.TaskResult

interface ResultGatherer {
    val taskResult: TaskResult
    var results: MutableList<StepResult>

    fun store(stepResult: StepResult)
    fun retrieve(identifier: StepIdentifier): StepResult?
}
