package com.quickbirdstudios.surveykit.backend.address

import android.content.Context
import android.location.Address
import android.location.Geocoder
import com.quickbirdstudios.surveykit.AnswerFormat
import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GeocoderAddressSuggestionProvider(
    val context: Context,
    private val maxResults: Int = 10,
    override var onSuggestionListReady: (suggestions: List<AddressSuggestion>) -> Unit? = {}
) : AddressSuggestionProvider {

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e(this::class.simpleName, "Error: $exception")
    }

    override fun input(query: String) {
        GlobalScope.launch(Dispatchers.Default + exceptionHandler) {
            val addresses = Geocoder(context).getFromLocationName(query, maxResults).map {
                AddressSuggestion(
                    it.getAddress(),
                    AnswerFormat.LocationAnswerFormat.Location(it.latitude, it.longitude)
                )
            }.toList()
            launch(Dispatchers.Main) {
                onSuggestionListReady.invoke(addresses)
            }
        }
    }

    private fun Address.getAddress(): String {
        var address = ""
        for (i in 0..maxAddressLineIndex) {
            address += getAddressLine(i)
        }
        return address
    }
}
