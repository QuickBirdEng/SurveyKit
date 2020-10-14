package com.quickbirdstudios.surveykit.backend.address

import com.quickbirdstudios.surveykit.AnswerFormat

abstract class AddressSuggestionProvider(
    var onSuggestionListReady: (suggestions: List<AddressSuggestion>) -> Unit?
) {

    abstract fun input(query: String)

    data class AddressSuggestion(
        val text: String,
        val location: AnswerFormat.LocationAnswerFormat.Location
    ) {
        override fun toString(): String = text
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as AddressSuggestion

            if (text != other.text) return false
            if (location != other.location) return false

            return true
        }

        override fun hashCode(): Int {
            var result = text.hashCode()
            result = 31 * result + location.hashCode()
            return result
        }
    }
}
