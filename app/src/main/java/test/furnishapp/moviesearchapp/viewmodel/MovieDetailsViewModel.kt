package test.furnishapp.moviesearchapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import test.furnishapp.moviesearchapp.api.MovieApi
import test.furnishapp.moviesearchapp.model.Movie

class MovieDetailsViewModel : ViewModel() {
    
    private val _movieDetails = MutableLiveData<Movie?>()
    val movieDetails: LiveData<Movie?> = _movieDetails
    
    fun getMovieDetails(imdbId: String) {
        MovieApi.getMovieDetails(imdbId) { movie ->
            _movieDetails.postValue(movie)
        }
    }
}
