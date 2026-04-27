package com.example.sdangol1_a3.data

// Movie returned from the search API before it is saved locally
data class SearchMovie(
    val imdbId: String,
    val title: String,
    val description: String,
    val year: String,
    val imageUrl: String = "",
    val averageRating: String = "",
    val genres: List<String> = emptyList()
)
