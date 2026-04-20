package com.example.sdangol1_a3.ui.viewmodel.effect

sealed class MovieDetailEffect : MovieEffect() {
    object DeleteSucceeded : MovieDetailEffect()
    object DeleteFailed : MovieDetailEffect()
}
