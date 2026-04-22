package com.example.sdangol1_a3.ui.viewmodel.effect

sealed class MovieDetailEffect : MovieEffect() {
    data class DeleteSucceeded(val movieTitle: String) : MovieDetailEffect()
    object DeleteFailed : MovieDetailEffect()
}
