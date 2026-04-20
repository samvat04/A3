package com.example.sdangol1_a3.ui.search.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.sdangol1_a3.data.SearchMovie
import com.example.sdangol1_a3.ui.common.MovieSummaryCard

@Composable
fun SearchResultItem(
    movie: SearchMovie,
    modifier: Modifier = Modifier,
    onSelectMovie: (SearchMovie) -> Unit = {}
) {
    MovieSummaryCard(
        title = movie.title,
        description = movie.description,
        year = movie.year,
        modifier = modifier,
        onClick = { onSelectMovie(movie) }
    )
}
