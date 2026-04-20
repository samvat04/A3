package com.example.sdangol1_a3.ui.viewmodel.intent

import com.example.sdangol1_a3.data.Movie

sealed class MovieListIntent : MovieIntent() {
    data class ToggleFavorite(val movie: Movie) : MovieListIntent()
}
