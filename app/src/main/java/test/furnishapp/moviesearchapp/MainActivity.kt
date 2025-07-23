package test.furnishapp.moviesearchapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import test.furnishapp.moviesearchapp.databinding.ActivityMainBinding
import test.furnishapp.moviesearchapp.adapter.MovieAdapter
import test.furnishapp.moviesearchapp.api.MovieApi
import test.furnishapp.moviesearchapp.model.Movie
import test.furnishapp.moviesearchapp.viewmodel.MovieViewModel
import androidx.lifecycle.ViewModelProvider
import android.view.View

class MainActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MovieAdapter
    private lateinit var viewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = MovieAdapter(mutableListOf()) { selectedMovie ->
            val intent = Intent(this, MovieDetailsActivity::class.java)
            intent.putExtra("title", selectedMovie.Title)
            intent.putExtra("year", selectedMovie.Year)
            intent.putExtra("type", selectedMovie.Type)
            intent.putExtra("poster", selectedMovie.Poster)
            startActivity(intent)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]

        viewModel.movies.observe(this) { movieList ->
            adapter.updateMovies(movieList)
            binding.recyclerView.visibility = if (movieList.isNotEmpty()) View.VISIBLE else View.GONE
        }

        binding.searchButton.setOnClickListener {
            val query = binding.searchEditText.text.toString()
            if (query.isNotEmpty()) {
                viewModel.search(query)
            }
        }
    }

}