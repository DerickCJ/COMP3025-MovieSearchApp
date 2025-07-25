package test.furnishapp.moviesearchapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import test.furnishapp.moviesearchapp.adapter.MovieAdapter
import test.furnishapp.moviesearchapp.databinding.ActivityMainBinding
import test.furnishapp.moviesearchapp.viewmodel.MovieViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MovieViewModel
    private lateinit var adapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize ViewModel
        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]

        // Setup RecyclerView
        setupRecyclerView()

        // Setup observers for ViewModel
        setupObservers()

        // Setup search functionality
        binding.searchButton.setOnClickListener {
            val query = binding.searchEditText.text.toString().trim()
            if (query.isNotEmpty() && query.length >= 2) {
                // Clear the previous search results
                binding.recyclerView.visibility = android.view.View.GONE
                viewModel.search(query)
            } else {
                Toast.makeText(this, R.string.search_hint_short, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = MovieAdapter(mutableListOf()) { selectedMovie ->
            val intent = Intent(this, MovieDetailsActivity::class.java)
            intent.putExtra("imdbId", selectedMovie.imdbID)
            startActivity(intent)
        }
        
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.movies.observe(this) { movieList ->
            adapter.updateMovies(movieList)
            // Show or hide the RecyclerView based on the movie list
            if (movieList.isNotEmpty()) {
                binding.recyclerView.visibility = android.view.View.VISIBLE
            } else {
                binding.recyclerView.visibility = android.view.View.GONE
                Toast.makeText(this, R.string.no_movies_found, Toast.LENGTH_SHORT).show()
            }
        }
    }
}