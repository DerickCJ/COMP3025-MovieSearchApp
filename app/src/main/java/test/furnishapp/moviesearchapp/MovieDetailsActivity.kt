package test.furnishapp.moviesearchapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import test.furnishapp.moviesearchapp.databinding.MovieDetailsBinding
import test.furnishapp.moviesearchapp.model.Movie
import test.furnishapp.moviesearchapp.viewmodel.MovieDetailsViewModel

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var binding: MovieDetailsBinding
    private lateinit var viewModel: MovieDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize ViewModel
        viewModel = ViewModelProvider(this)[MovieDetailsViewModel::class.java]

        // Setup observers for ViewModel
        setupObservers()

        // Get the IMDB ID from the intent
        val imdbId = intent.getStringExtra("imdbId")!!
        viewModel.getMovieDetails(imdbId)

        // Setup back button functionality
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun setupObservers() {
        viewModel.movieDetails.observe(this) { movie ->
            movie?.let { displayMovieDetails(it) }
        }
    }

    private fun displayMovieDetails(movie: Movie) {
        binding.apply {
            detailTitleTextView.text = movie.Title
            detailYearTextView.text = "Year: ${movie.Year}"
            detailGenreTextView.text = "Genre: ${movie.Genre?.takeIf { it.isNotBlank() } ?: "Not available"}"
            detailDirectorTextView.text = "Director: ${movie.Director?.takeIf { it.isNotBlank() } ?: "Not available"}"
            detailActorsTextView.text = "Actors: ${movie.Actors?.takeIf { it.isNotBlank() } ?: "Not available"}"
            detailRatingTextView.text = "IMDB Rating: ${movie.imdbRating?.takeIf { it.isNotBlank() } ?: "Not rated"}"
            detailPlotLabel.text = "Plot:"
            detailPlotTextView.text = movie.Plot?.takeIf { it.isNotBlank() } ?: "No plot available"

            Glide.with(this@MovieDetailsActivity)
                .load(movie.Poster)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_report_image)
                .into(detailPosterImageView)
        }
    }
}
