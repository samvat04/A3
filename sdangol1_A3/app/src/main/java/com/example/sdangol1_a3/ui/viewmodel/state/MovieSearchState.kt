package com.example.sdangol1_a3.ui.viewmodel.state

import com.example.sdangol1_a3.data.SearchMovie

data class MovieSearchState(
    val query: String = "",
    val loading: Boolean = false,
    val results: List<SearchMovie> = emptyList(),
    val selectedMovie: SearchMovie? = null
) : MovieState
