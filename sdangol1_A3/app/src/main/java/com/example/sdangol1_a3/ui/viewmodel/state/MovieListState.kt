package com.example.sdangol1_a3.ui.viewmodel.state

import com.example.sdangol1_a3.data.Movie
import com.example.sdangol1_a3.util.MovieFilterOption

data class MovieListState(
    val movies: List<Movie> = emptyList(),
    val loading: Boolean = true,
    val filter: MovieFilterOption = MovieFilterOption.ALL
) : MovieState
