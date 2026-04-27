package com.example.sdangol1_a3.ui.viewmodel.state

import com.example.sdangol1_a3.data.SearchMovie

data class MovieSearchState(
    // Current text entered in the search field
    val query: String = "",
    val loading: Boolean = false,
    // Current autocomplete results returned from the API
    val results: List<SearchMovie> = emptyList(),
    // Movie chosen from the results list for poster preview and saving
    val selectedMovie: SearchMovie? = null
) : MovieState
