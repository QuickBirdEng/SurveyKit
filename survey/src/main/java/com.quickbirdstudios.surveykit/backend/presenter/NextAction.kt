package com.quickbirdstudios.surveykit.backend.presenter

import com.quickbirdstudios.surveykit.FinishReason
import com.quickbirdstudios.surveykit.result.StepResult

sealed class NextAction {
    data class Next(val result: StepResult) : NextAction()
    data class Back(val result: StepResult) : NextAction()
    object Skip : NextAction()
    data class Close(val result: StepResult, val finishReason: FinishReason) : NextAction()
}
