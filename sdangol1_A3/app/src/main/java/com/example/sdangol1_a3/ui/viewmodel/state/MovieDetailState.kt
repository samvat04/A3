package com.example.sdangol1_a3.ui.viewmodel.state

import com.example.sdangol1_a3.data.CastMember
import com.example.sdangol1_a3.data.Movie

data class MovieDetailState(
    val movie: Movie? = null,
    val cast: List<CastMember> = emptyList(),
    val loadingCast: Boolean = false
) : MovieState
