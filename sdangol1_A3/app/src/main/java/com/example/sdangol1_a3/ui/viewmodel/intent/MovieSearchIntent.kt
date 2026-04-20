package com.example.sdangol1_a3.ui.viewmodel.intent

import com.example.sdangol1_a3.data.SearchMovie

sealed class MovieSearchIntent : MovieIntent() {
    data class UpdateQuery(val query: String) : MovieSearchIntent()
    data class Search(val query: String) : MovieSearchIntent()
    data class SelectMovie(val movie: SearchMovie) : MovieSearchIntent()
    object SaveSelectedMovie : MovieSearchIntent()
}
