package test.furnishapp.moviesearchapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.json.JSONObject
import test.furnishapp.moviesearchapp.api.MovieApi
import test.furnishapp.moviesearchapp.model.Movie

class MovieViewModel : ViewModel() {

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    fun search(query: String) {
        MovieApi.searchMovies(query) { result ->
            if (result != null)
            {
                val json = JSONObject(result)
                val response = json.optString("Response", "False")
                if (response == "True")
                {
                    val array = json.getJSONArray("Search")
                    val list = mutableListOf<Movie>()
                    for (i in 0 until array.length())
                    {
                        val item = array.getJSONObject(i)
                        val movie = Movie(
                            Title = item.getString("Title"),
                            Year = item.getString("Year"),
                            imdbID = item.getString("imdbID"),
                            Type = item.getString("Type"),
                            Poster = item.getString("Poster")
                        )
                        list.add(movie)
                    }
                    _movies.postValue(list)
                }
                else
                {
                    _movies.postValue(emptyList())  // Return empty list if no results found
                }
            }
            else
            {
                _movies.postValue(emptyList())      // Something went wrong, return empty list
            }
        }
    }
}
