package com.quickbirdstudios.surveykit.backend.address

import com.quickbirdstudios.surveykit.AnswerFormat
import java.io.FileNotFoundException
import java.net.HttpURLConnection
import java.net.URL
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class YandexAddressSuggestionProvider(
    private val apiKey: String,
    private val resultsCount: Int = 5,
    private val lang: String = "tr_TR",
    onSuggestionListReady: (suggestions: List<AddressSuggestion>) -> Unit? = {}
) : AddressSuggestionProvider(onSuggestionListReady) {

    private fun getApiPath(apiKey: String, query: String, results: Int, lang: String) =
        "https://geocode-maps.yandex.ru/1.x/?apikey=$apiKey" +
            "&format=json&geocode=$query" +
            "&results=$results" +
            "&lang=$lang"

    override fun input(query: String) {
        val apiPath =
            getApiPath(apiKey = apiKey, query = query, results = resultsCount, lang = lang)

        GlobalScope.launch(Dispatchers.Main) { // launches coroutine in main thread
            val result = fetchResults(apiPath)
            onSuggestionListReady.invoke(result)
        }
    }

    private suspend fun fetchResults(apiPath: String): MutableList<AddressSuggestion> {
        val suggestions: Deferred<MutableList<AddressSuggestion>> = GlobalScope.async {

            val suggestions: MutableList<AddressSuggestion> = mutableListOf()

            try {
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
                        AddressSuggestion(text = name + "\n" + description, location = location)
                    suggestions.add(suggestion)
                }
            } catch (exception: JSONException) {
                // ignore
            } catch (exception: FileNotFoundException) {
                // ignore
            }

            return@async suggestions
        }

        return suggestions.await()
    }

    private fun URL.getText(): String {
        return openConnection().run {
            this as HttpURLConnection
            inputStream.bufferedReader().readText()
        }
    }
}
