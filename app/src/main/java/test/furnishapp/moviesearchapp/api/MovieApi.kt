package test.furnishapp.moviesearchapp

import okhttp3.*
import java.io.IOException

object MovieApi {
    private val client = OkHttpClient()

    fun searchMovies(query: String, callback: (String?) -> Unit) {
        val apiKey = "38fac44"
        val url = "https://www.omdbapi.com/?apikey=$apiKey&s=${query}"

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback(null)
            }

            override fun onResponse(call: Call, response: Response) {
                val result = response.body?.string()
                callback(result)
            }
        })
    }
}
