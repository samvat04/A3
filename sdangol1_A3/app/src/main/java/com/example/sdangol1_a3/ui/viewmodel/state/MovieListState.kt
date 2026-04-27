package com.example.sdangol1_a3.ui.viewmodel.state

import com.example.sdangol1_a3.data.Movie
import com.example.sdangol1_a3.util.MovieFilterOption

data class MovieListState(
    // Currently visible movie list after applying the active filter
    val movies: List<Movie> = emptyList(),
    val loading: Boolean = true,
    // Tracks whether the screen should show all movies or only favorites
    val filter: MovieFilterOption = MovieFilterOption.ALL
) : MovieState
