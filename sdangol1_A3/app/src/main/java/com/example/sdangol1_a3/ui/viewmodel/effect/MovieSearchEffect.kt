package com.example.sdangol1_a3.ui.viewmodel.effect

import com.example.sdangol1_a3.data.Movie

sealed class MovieSearchEffect : MovieEffect() {
    data class SaveSucceeded(val movie: Movie) : MovieSearchEffect()
    data class MovieAlreadyExists(val title: String) : MovieSearchEffect()
    object SearchFailed : MovieSearchEffect()
}
