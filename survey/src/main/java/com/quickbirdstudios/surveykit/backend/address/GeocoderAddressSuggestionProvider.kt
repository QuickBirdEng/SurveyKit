package com.quickbirdstudios.surveykit.backend.address

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import com.quickbirdstudios.surveykit.AnswerFormat
import kotlinx.coroutines.*

class GeocoderAddressSuggestionProvider(
    val context: Context,
    private val maxResults: Int = 10,
    override var onSuggestionListReady: (suggestions: List<AddressSuggestion>) -> Unit? = {}
) : AddressSuggestionProvider {

    override fun input(coroutineScope: CoroutineScope, query: String) {
        coroutineScope.launch(Dispatchers.Default + exceptionHandler) {
            val addresses = retrieveAddresses(query)
            withContext(Dispatchers.Main) { onSuggestionListReady.invoke(addresses) }
        }
    }

    private fun retrieveAddresses(query: String): List<AddressSuggestion> {
        return  Geocoder(context).getFromLocationName(query, maxResults).map {
            AddressSuggestion(
                it.getAddress(),
                AnswerFormat.LocationAnswerFormat.Location(it.latitude, it.longitude)
            )
        }.toList()
    }

    private fun Address.getAddress(): String {
        var address = ""
        for (i in 0..maxAddressLineIndex) {
            address += getAddressLine(i)
        }
        return address
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("GeocoderAddressProvider", "exception thrown: ", throwable)
    }
}
