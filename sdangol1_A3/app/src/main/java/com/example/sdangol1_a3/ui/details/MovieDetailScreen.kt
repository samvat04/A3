package com.example.sdangol1_a3.ui.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sdangol1_a3.data.CastMember
import com.example.sdangol1_a3.data.Movie
import com.example.sdangol1_a3.ui.common.LabelText
import com.example.sdangol1_a3.ui.common.MovieButton
import com.example.sdangol1_a3.ui.common.MoviePosterImage
import com.example.sdangol1_a3.ui.common.ValueText
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
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            MoviePosterImage(
                imageUrl = movie.imageUrl,
                contentDescription = movie.title,
                modifier = Modifier.weight(1f)
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                LabelText("Title")
                ValueText(movie.title)

                LabelText("Year")
                ValueText(movie.year)

                LabelText("IMDb Rating")
                ValueText(movie.averageRating.ifBlank { "N/A" })

                LabelText("Genres")
                ValueText(
                    if (movie.genres.isEmpty()) "N/A"
                    else movie.genres.joinToString(", ")
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            LabelText("Description")
            ValueText(movie.description)
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            MovieButton(
                text = "View Cast",
                onClick = onLoadCast,
                enabled = castButtonEnabled
            )
        }

        if (cast.isNotEmpty()) {
            CastList(
                cast = cast,
                onViewPerson = onViewPerson
            )
        }
    }
}
