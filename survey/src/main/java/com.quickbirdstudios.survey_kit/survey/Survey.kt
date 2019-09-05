package com.quickbirdstudios.survey_kit.survey

import com.quickbirdstudios.survey_kit.FinishReason
import com.quickbirdstudios.survey_kit.SurveyTheme
import com.quickbirdstudios.survey_kit.Task
import com.quickbirdstudios.survey_kit.result.StepResult
import com.quickbirdstudios.survey_kit.result.TaskResult
import com.quickbirdstudios.survey_kit.steps.Step


internal interface Survey {
    var onStepResult: (Step?, StepResult?) -> Unit
    var onSurveyFinish: (TaskResult, FinishReason) -> Unit

    fun start(task: Task, surveyTheme: SurveyTheme)
    fun backPressed()
}

