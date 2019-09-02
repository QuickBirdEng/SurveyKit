package com.quickbirdstudios.survey_kit.public_api.survey

import com.quickbirdstudios.survey_kit.public_api.FinishReason
import com.quickbirdstudios.survey_kit.public_api.SurveyTheme
import com.quickbirdstudios.survey_kit.public_api.Task
import com.quickbirdstudios.survey_kit.public_api.result.StepResult
import com.quickbirdstudios.survey_kit.public_api.result.TaskResult
import com.quickbirdstudios.survey_kit.public_api.steps.Step


internal interface Survey {
    var onStepResult: (Step?, StepResult?) -> Unit
    var onSurveyFinish: (TaskResult, FinishReason) -> Unit

    fun start(task: Task, surveyTheme: SurveyTheme)
    fun backPressed()
}

