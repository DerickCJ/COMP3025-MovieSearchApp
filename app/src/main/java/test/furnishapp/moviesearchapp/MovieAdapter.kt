package test.furnishapp.moviesearchapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import test.furnishapp.moviesearchapp.databinding.ItemMovieBinding

class MovieAdapter(
    private val movieList: MutableList<Movie>,
    private val onItemClick: (Movie) -> Unit
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position]
        holder.binding.apply {
            titleTextView.text = movie.Title
            yearTextView.text = movie.Year
            genreTextView.text = movie.Type

            // 使用 Glide 加载电影海报
            Glide.with(posterImageView.context)
                .load(movie.Poster)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_report_image)
                .transition(DrawableTransitionOptions.withCrossFade())
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
