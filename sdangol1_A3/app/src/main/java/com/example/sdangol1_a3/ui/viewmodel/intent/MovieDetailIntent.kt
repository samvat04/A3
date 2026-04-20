package com.example.sdangol1_a3.ui.viewmodel.intent

import com.example.sdangol1_a3.data.Movie
import java.util.UUID

sealed class MovieDetailIntent : MovieIntent() {
    data class LoadMovie(val movieId: UUID) : MovieDetailIntent()
    data class DeleteMovie(val movie: Movie) : MovieDetailIntent()
    data class FetchCast(val imdbId: String) : MovieDetailIntent()
}
