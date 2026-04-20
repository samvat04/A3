package com.example.sdangol1_a3.ui.viewmodel.state

import com.example.sdangol1_a3.data.Movie


data class MovieListState(
    val loading: Boolean = true,
    val movies: List<Movie> = emptyList()
) : MovieState
