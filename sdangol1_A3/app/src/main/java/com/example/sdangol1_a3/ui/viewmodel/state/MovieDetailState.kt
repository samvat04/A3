package com.example.sdangol1_a3.ui.viewmodel.state

import com.example.sdangol1_a3.data.CastMember
import com.example.sdangol1_a3.data.Movie

data class MovieDetailState(
    // Holds the locally loaded movie displayed on the detail screen
    val movie: Movie? = null,
    // Stores cast data after the user requests it from the API
    val cast: List<CastMember> = emptyList(),
    // Allows the UI to react while cast loading is in progress
    val loadingCast: Boolean = false
) : MovieState
