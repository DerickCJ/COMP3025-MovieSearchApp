package test.furnishapp.moviesearchapp

import android.content.Intent
import android.os.Bundle
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
                viewModel.search(query)
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
        }
    }
}