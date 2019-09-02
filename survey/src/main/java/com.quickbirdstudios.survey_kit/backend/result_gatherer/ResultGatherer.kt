package com.quickbirdstudios.survey_kit.backend.result_gatherer

import com.quickbirdstudios.survey_kit.public_api.StepIdentifier
import com.quickbirdstudios.survey_kit.public_api.result.StepResult
import com.quickbirdstudios.survey_kit.public_api.result.TaskResult

interface ResultGatherer {
    val taskResult: TaskResult
    var results: MutableList<StepResult>

    fun store(stepResult: StepResult)
    fun retrieve(identifier: StepIdentifier): StepResult?
}
