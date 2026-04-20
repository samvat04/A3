package com.example.sdangol1_a3.ui.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sdangol1_a3.data.CastMember
import com.example.sdangol1_a3.data.Movie
import com.example.sdangol1_a3.ui.common.MovieAttributeDisplay
import com.example.sdangol1_a3.ui.common.MovieButton
import com.example.sdangol1_a3.ui.common.MoviePosterImage
import com.example.sdangol1_a3.ui.common.SectionHeader
import com.example.sdangol1_a3.ui.details.components.CastList

@Composable
fun MovieDetailScreen(
    movie: Movie,
    cast: List<CastMember>,
    modifier: Modifier = Modifier,
    castButtonEnabled: Boolean = true,
    onLoadCast: () -> Unit = {},
    onViewPerson: (CastMember) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            MoviePosterImage(
                imageUrl = movie.imageUrl,
                contentDescription = movie.title
            )
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                MovieAttributeDisplay("Title", movie.title)
                MovieAttributeDisplay("Description", movie.description)
                MovieAttributeDisplay("Year", movie.year)
                MovieAttributeDisplay("Rating", movie.averageRating ?: "N/A")
                MovieAttributeDisplay("Genres", movie.genres.joinToString())
            }
        }

        item {
            MovieButton(
                text = "View Cast",
                onClick = onLoadCast,
                enabled = castButtonEnabled
            )
        }

        item {
            SectionHeader("Cast")
        }

        item {
            CastList(
                cast = cast,
                onViewPerson = onViewPerson
            )
        }
    }
}
