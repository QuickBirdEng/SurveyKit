package com.quickbirdstudios.surveykit.survey

import com.quickbirdstudios.surveykit.FinishReason
import com.quickbirdstudios.surveykit.SurveyTheme
import com.quickbirdstudios.surveykit.Task
import com.quickbirdstudios.surveykit.result.StepResult
import com.quickbirdstudios.surveykit.result.TaskResult
import com.quickbirdstudios.surveykit.steps.Step

internal interface Survey {
    var onStepResult: (Step?, StepResult?) -> Unit
    var onSurveyFinish: (TaskResult, FinishReason) -> Unit

    fun start(task: Task, surveyTheme: SurveyTheme)
    fun backPressed()
}
