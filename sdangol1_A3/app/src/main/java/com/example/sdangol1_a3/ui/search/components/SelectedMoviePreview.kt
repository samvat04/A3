package com.example.sdangol1_a3.ui.search.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import com.example.sdangol1_a3.data.SearchMovie
import com.example.sdangol1_a3.ui.common.MoviePosterImage

@Composable
fun SelectedMoviePreview(
    selectedMovie: SearchMovie?,
    modifier: Modifier = Modifier
) {
    if (selectedMovie == null) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Select a movie to preview its poster")
        }
    } else {
        MoviePosterImage(
            imageUrl = selectedMovie.imageUrl,
            contentDescription = selectedMovie.title,
            modifier = modifier.fillMaxWidth()
        )
    }
}
