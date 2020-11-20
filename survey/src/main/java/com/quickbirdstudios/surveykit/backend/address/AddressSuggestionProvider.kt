package com.quickbirdstudios.surveykit.backend.address

import com.quickbirdstudios.surveykit.AnswerFormat
import kotlinx.coroutines.CoroutineScope

interface AddressSuggestionProvider {
    var onSuggestionListReady: (suggestions: List<AddressSuggestion>) -> Unit?

    fun input(coroutineScope: CoroutineScope, query: String)
}

data class AddressSuggestion(
    val text: String,
    val location: AnswerFormat.LocationAnswerFormat.Location
) {
    override fun toString(): String = text
}
