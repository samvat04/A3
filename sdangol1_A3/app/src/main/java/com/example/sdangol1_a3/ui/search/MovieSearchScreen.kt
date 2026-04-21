package com.example.sdangol1_a3.ui.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sdangol1_a3.data.SearchMovie
import com.example.sdangol1_a3.ui.search.components.MovieSearchBar
import com.example.sdangol1_a3.ui.search.components.SaveMovieButton
import com.example.sdangol1_a3.ui.search.components.SearchResultList
import com.example.sdangol1_a3.ui.search.components.SelectedMoviePreview

@Composable
fun MovieSearchScreen(
    query: String,
    results: List<SearchMovie>,
    selectedMovie: SearchMovie?,
    modifier: Modifier = Modifier,
    searchEnabled: Boolean = true,
    saveEnabled: Boolean = selectedMovie != null,
    onQueryChange: (String) -> Unit = {},
    onSearch: () -> Unit = {},
    onSelectMovie: (SearchMovie) -> Unit = {},
    onSaveMovie: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            MovieSearchBar(
                query = query,
                onQueryChange = onQueryChange,
                onSearch = onSearch,
                searchEnabled = searchEnabled,
                modifier = Modifier.fillMaxWidth()
            )

            SearchResultList(
                results = results,
                onSelectMovie = onSelectMovie,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(top = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SelectedMoviePreview(
                selectedMovie = selectedMovie,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

            SaveMovieButton(
                enabled = saveEnabled,
                onSaveMovie = onSaveMovie,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
