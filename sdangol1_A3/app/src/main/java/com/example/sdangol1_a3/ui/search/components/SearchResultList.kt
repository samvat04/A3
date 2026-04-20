package com.example.sdangol1_a3.ui.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sdangol1_a3.data.SearchMovie

@Composable
fun SearchResultList(
    results: List<SearchMovie>,
    modifier: Modifier = Modifier,
    onSelectMovie: (SearchMovie) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(results) { movie ->
            SearchResultItem(
                movie = movie,
                onSelectMovie = onSelectMovie
            )
        }
    }
}
