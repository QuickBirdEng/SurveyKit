package com.quickbirdstudios.surveykit.backend.views.step

import com.quickbirdstudios.surveykit.FinishReason
import com.quickbirdstudios.surveykit.result.QuestionResult

interface ViewActions : Skipable,
    Identifiable {
    fun onNext(block: (QuestionResult) -> Unit)
    fun onBack(block: (QuestionResult) -> Unit)
    fun onClose(block: (QuestionResult, FinishReason) -> Unit)

    fun createResults(): QuestionResult
    fun isValidInput(): Boolean

    fun back()
}
