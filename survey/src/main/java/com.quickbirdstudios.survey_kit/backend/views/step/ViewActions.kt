package com.quickbirdstudios.survey_kit.backend.views.step

import com.quickbirdstudios.survey_kit.public_api.FinishReason
import com.quickbirdstudios.survey_kit.public_api.result.QuestionResult

interface ViewActions : Skipable, Identifiable {
    fun onNext(block: (QuestionResult) -> Unit)
    fun onBack(block: (QuestionResult) -> Unit)
    fun onClose(block: (QuestionResult, FinishReason) -> Unit)

    fun createResults(): QuestionResult
    fun isValidInput(): Boolean

    fun back()
}
