package com.example.sdangol1_a3.ui.list

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.sdangol1_a3.data.Movie
import com.example.sdangol1_a3.ui.list.components.EmptyMovieListContent
import com.example.sdangol1_a3.ui.list.components.MovieList

@Composable
fun MovieListScreen(
    movies: List<Movie>,
    modifier: Modifier = Modifier,
    onSelectMovie: (Movie) -> Unit = {},
    onToggleFavorite: (Movie) -> Unit = {}
) {
    if(movies.isEmpty()) {
        EmptyMovieListContent(modifier = modifier)
    } else {
        MovieList(
            movies = movies,
            modifier = modifier,
            onSelectMovie = onSelectMovie,
            onToggleFavorite = onToggleFavorite
        )
    }
}
