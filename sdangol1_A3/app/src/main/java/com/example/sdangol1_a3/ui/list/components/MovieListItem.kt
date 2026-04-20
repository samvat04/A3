package com.example.sdangol1_a3.ui.list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.sdangol1_a3.data.Movie
import com.example.sdangol1_a3.ui.common.FavoriteToggleButton
import com.example.sdangol1_a3.ui.common.MovieSummaryCard

@Composable
fun MovieListItem(
    movie: Movie,
    modifier: Modifier = Modifier,
    onSelectMovie: (Movie) -> Unit = {},
    onToggleFavorite: (Movie) -> Unit = {}
) {
    MovieSummaryCard(
        title = movie.title,
        description = movie.description,
        year = movie.year,
        modifier = modifier,
        onClick = { onSelectMovie(movie) },
        trailingContent = {
            Row(horizontalArrangement = Arrangement.End) {
                FavoriteToggleButton(
                    isFavorite = movie.isFavorite,
                    onToggle = { onToggleFavorite(movie) }
                )
            }
        }
    )
}
