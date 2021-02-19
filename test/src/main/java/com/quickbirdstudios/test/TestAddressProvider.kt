package com.quickbirdstudios.test

import com.quickbirdstudios.surveykit.AnswerFormat
import com.quickbirdstudios.surveykit.backend.address.AddressSuggestion
import com.quickbirdstudios.surveykit.backend.address.AddressSuggestionProvider

class TestAddressProvider(
    override var onSuggestionListReady: (suggestions: List<AddressSuggestion>) -> Unit? = {}
) : AddressSuggestionProvider {

    override fun input(query: String) {
        val suggestions = mutableListOf(
            AddressSuggestion(
                "test1",
                AnswerFormat.LocationAnswerFormat.Location(1.0, 1.0)
            ),
            AddressSuggestion(
                "test2",
                AnswerFormat.LocationAnswerFormat.Location(2.0, 2.0)
            ),
            AddressSuggestion(
                "test3",
                AnswerFormat.LocationAnswerFormat.Location(3.0, 3.0)
            ),
            AddressSuggestion(
                "test4",
                AnswerFormat.LocationAnswerFormat.Location(4.0, 4.0)
            )
        )
        onSuggestionListReady.invoke(suggestions)
    }
}
