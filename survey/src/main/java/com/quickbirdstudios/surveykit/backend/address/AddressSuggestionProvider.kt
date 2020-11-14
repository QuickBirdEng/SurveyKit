package com.quickbirdstudios.surveykit.backend.address

import com.quickbirdstudios.surveykit.AnswerFormat

interface AddressSuggestionProvider {

    var onSuggestionListReady: (suggestions: List<AddressSuggestion>) -> Unit?

    fun input(query: String)

}

data class AddressSuggestion(
    val text: String,
    val location: AnswerFormat.LocationAnswerFormat.Location
) {
    override fun toString(): String = text
}
