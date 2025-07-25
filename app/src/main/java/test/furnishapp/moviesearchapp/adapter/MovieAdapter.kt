package test.furnishapp.moviesearchapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import test.furnishapp.moviesearchapp.databinding.ItemMovieBinding
import test.furnishapp.moviesearchapp.model.Movie

class MovieAdapter(
    private var movieList: MutableList<Movie>,
    private val onItemClick: (Movie) -> Unit
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder
    {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position]
        holder.binding.apply {
            titleTextView.text = movie.Title
            yearTextView.text = movie.Year
            genreTextView.text = movie.Type
            imdbIdTextView.text = "IMDb: ${movie.imdbID}"

            // Use Glide to load the poster image
            Glide.with(posterImageView.context)
                .load(movie.Poster)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_report_image)
                .into(posterImageView)

            root.setOnClickListener {
                onItemClick(movie)
            }
        }
    }

    override fun getItemCount(): Int = movieList.size

    fun updateMovies(newList: List<Movie>) {
        movieList.clear()
        movieList.addAll(newList)
        notifyDataSetChanged()
    }
}
