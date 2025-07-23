package test.furnishapp.moviesearchapp.api

import okhttp3.*
import org.json.JSONObject
import test.furnishapp.moviesearchapp.model.Movie
import java.io.IOException

object MovieApi {
    private val client = OkHttpClient()
    private const val apiKey = "38fac44"

    fun searchMovies(query: String, callback: (List<Movie>) -> Unit) {
        val url = "https://www.omdbapi.com/?apikey=$apiKey&s=${query.trim()}"

        val request = Request.Builder().url(url).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback(emptyList())
            }

            override fun onResponse(call: Call, response: Response) {
                val movieList = mutableListOf<Movie>()
                response.body?.string()?.let { jsonString ->
                    val json = JSONObject(jsonString)
                    if (json.optString("Response") == "True") {
                        json.optJSONArray("Search")?.let { array ->
                            for (i in 0 until array.length()) {
                                array.optJSONObject(i)?.run {
                                    movieList.add(
                                        Movie(
                                            Title = optString("Title"),
                                            Year = optString("Year"),
                                            Type = optString("Type"),
                                            Poster = optString("Poster"),
                                            imdbID = optString("imdbID")
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
                callback(movieList)
            }
        })
    }
}
