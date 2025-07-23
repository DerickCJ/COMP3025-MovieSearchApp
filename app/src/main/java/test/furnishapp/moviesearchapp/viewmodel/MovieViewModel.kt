package test.furnishapp.moviesearchapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import test.furnishapp.moviesearchapp.api.MovieApi
import test.furnishapp.moviesearchapp.model.Movie

class MovieViewModel : ViewModel() {

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    fun search(query: String) {
        MovieApi.searchMovies(query) { resultList ->
            _movies.postValue(resultList)
        }
    }
}
