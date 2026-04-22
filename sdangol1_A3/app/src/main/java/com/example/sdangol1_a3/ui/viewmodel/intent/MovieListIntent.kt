package com.example.sdangol1_a3.ui.viewmodel.intent

import com.example.sdangol1_a3.data.Movie
import com.example.sdangol1_a3.util.MovieFilterOption

sealed class MovieListIntent : MovieIntent() {
    data class ToggleFavorite(val movie: Movie) : MovieListIntent()
    data class SetFilter(val filter: MovieFilterOption) : MovieListIntent()
}
