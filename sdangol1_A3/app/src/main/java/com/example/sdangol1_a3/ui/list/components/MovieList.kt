package com.example.sdangol1_a3.ui.list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sdangol1_a3.data.Movie

@Composable
fun MovieList(
    movies: List<Movie>,
    modifier: Modifier = Modifier,
    onSelectMovie: (Movie) -> Unit = {},
    onToggleFavorite: (Movie) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(movies) { movie ->
            MovieListItem(
                movie = movie,
                onSelectMovie = onSelectMovie,
                onToggleFavorite = onToggleFavorite
            )
        }
    }
}
