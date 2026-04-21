package com.example.sdangol1_a3.ui.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sdangol1_a3.data.SearchMovie
import com.example.sdangol1_a3.ui.common.MovieAttributeDisplay
import com.example.sdangol1_a3.ui.common.MoviePosterImage
import com.example.sdangol1_a3.ui.common.SectionHeader

@Composable
fun SelectedMoviePreview(
    selectedMovie: SearchMovie?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SectionHeader("Selected Movie")

        if (selectedMovie != null) {
            MoviePosterImage(
                imageUrl = selectedMovie.imageUrl,
                contentDescription = selectedMovie.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = false)
            )
            MovieAttributeDisplay("Title", selectedMovie.title)
            MovieAttributeDisplay("Description", selectedMovie.description)
            MovieAttributeDisplay("Year", selectedMovie.year)
        }
    }
}
