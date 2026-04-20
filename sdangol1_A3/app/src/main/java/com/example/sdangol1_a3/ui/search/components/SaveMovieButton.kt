package com.example.sdangol1_a3.ui.search.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.sdangol1_a3.ui.common.MovieButton

@Composable
fun SaveMovieButton(
    enabled: Boolean,
    onSaveMovie: () -> Unit,
    modifier: Modifier = Modifier
) {
    MovieButton(
        text = "Save Movie",
        onClick = onSaveMovie,
        modifier = modifier,
        enabled = enabled
    )
}
