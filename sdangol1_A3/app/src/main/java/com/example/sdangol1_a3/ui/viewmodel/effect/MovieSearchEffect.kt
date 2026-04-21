package com.example.sdangol1_a3.ui.viewmodel.effect

sealed class MovieSearchEffect : MovieEffect() {
    object SaveSucceeded : MovieSearchEffect()
    object MovieAlreadyExists : MovieSearchEffect()
    object SearchFailed : MovieSearchEffect()
}
