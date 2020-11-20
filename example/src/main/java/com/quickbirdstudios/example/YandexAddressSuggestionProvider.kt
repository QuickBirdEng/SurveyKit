package com.quickbirdstudios.example

import android.util.Log
import com.quickbirdstudios.surveykit.AnswerFormat
import com.quickbirdstudios.surveykit.backend.address.AddressSuggestion
import com.quickbirdstudios.surveykit.backend.address.AddressSuggestionProvider
import kotlinx.coroutines.*
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class YandexAddressSuggestionProvider(
    private val apiKey: String,
    private val resultsCount: Int = 5,
    private val lang: String = "tr_TR",
    override var onSuggestionListReady: (suggestions: List<AddressSuggestion>) -> Unit? = {}
) : AddressSuggestionProvider {

    override fun input(coroutineScope: CoroutineScope, query: String) {
        val apiPath = getApiPath(
            apiKey = apiKey,
            query = query,
            results = resultsCount,
            lang = lang
        )

        coroutineScope.launch(Dispatchers.Default + exceptionHandler) {
            val result = withContext(Dispatchers.IO) { fetchResults(apiPath) }
            withContext(Dispatchers.Main) { onSuggestionListReady.invoke(result) }
        }
    }

    private fun getApiPath(apiKey: String, query: String, results: Int, lang: String) =
        "https://geocode-maps.yandex.ru/1.x/?apikey=$apiKey" +
            "&format=json&geocode=$query" +
            "&results=$results" +
            "&lang=$lang"

    private fun fetchResults(apiPath: String): MutableList<AddressSuggestion> {
            val suggestions: MutableList<AddressSuggestion> = mutableListOf()

            val response = URL(apiPath).getText()
            val json = JSONObject(response)["response"] as JSONObject
            val featureMember =
                (json["GeoObjectCollection"] as JSONObject)["featureMember"] as JSONArray

            for (i in 0 until featureMember.length()) {
                val item = featureMember.getJSONObject(i)
                val geoObject = (item["GeoObject"] as JSONObject)
                val pos = (geoObject["Point"] as JSONObject)["pos"] as String

                val name = geoObject["name"] as String
                val description = geoObject["description"] as String
                val latitude = pos.split(" ")[0].toDouble()
                val longitude = pos.split(" ")[1].toDouble()

                val location = AnswerFormat.LocationAnswerFormat.Location(latitude, longitude)

                val suggestion =
                    AddressSuggestion(
                        text = name + "\n" + description,
                        location = location
                    )
                suggestions.add(suggestion)
            }

            return suggestions
        }

    private fun URL.getText(): String {
        return openConnection().run {
            this as HttpURLConnection
            inputStream.bufferedReader().readText()
        }
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("YandexAddressSuggestion", "exception thrown: ", throwable)
    }
}
