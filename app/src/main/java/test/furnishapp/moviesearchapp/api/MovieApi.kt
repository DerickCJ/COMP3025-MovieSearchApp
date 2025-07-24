package test.furnishapp.moviesearchapp.api

import okhttp3.*
import org.json.JSONObject
import test.furnishapp.moviesearchapp.model.Movie
import java.io.IOException

object MovieApi {
    private val client = OkHttpClient()
    private const val API_KEY = "38fac44"
    private const val BASE_URL = "https://www.omdbapi.com/"

    fun searchMovies(query: String, callback: (List<Movie>) -> Unit) {
        val url = "${BASE_URL}?apikey=$API_KEY&s=${query.trim()}&type=movie"
        val request = Request.Builder().url(url).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback(emptyList())
            }

            override fun onResponse(call: Call, response: Response) {
                val movieList = mutableListOf<Movie>()
                try {
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
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                callback(movieList)
            }
        })
    }

    fun getMovieDetails(imdbId: String, callback: (Movie?) -> Unit) {
        val url = "${BASE_URL}?apikey=$API_KEY&i=${imdbId.trim()}"
        val request = Request.Builder().url(url).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback(null)
            }

            override fun onResponse(call: Call, response: Response) {
                var movie: Movie? = null
                try {
                    response.body?.string()?.let { jsonString ->
                        val json = JSONObject(jsonString)
                        if (json.optString("Response") == "True") {
                            movie = Movie(
                                Title = json.optString("Title"),
                                Year = json.optString("Year"),
                                imdbID = json.optString("imdbID"),
                                Type = json.optString("Type"),
                                Poster = json.optString("Poster"),
                                Director = json.optString("Director"),
                                Actors = json.optString("Actors"),
                                Plot = json.optString("Plot"),
                                Runtime = json.optString("Runtime"),
                                imdbRating = json.optString("imdbRating"),
                                Genre = json.optString("Genre")
                            )
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                callback(movie)
            }
        })
    }
}
