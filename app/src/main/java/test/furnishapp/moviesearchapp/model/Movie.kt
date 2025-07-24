package test.furnishapp.moviesearchapp.model

data class Movie(
    val Title: String,
    val Year: String,
    val imdbID: String,
    val Type: String,
    val Poster: String,
    // Values that may not always be present
    val Director: String? = null,
    val Actors: String? = null,
    val Plot: String? = null,
    val Runtime: String? = null,
    val imdbRating: String? = null,
    val Genre: String? = null
)
