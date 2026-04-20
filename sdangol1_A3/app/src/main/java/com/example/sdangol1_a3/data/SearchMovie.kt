package com.example.sdangol1_a3.data

data class SearchMovie(
    val imdbId: String,
    val title: String,
    val description: String,
    val year: String,
    val imageUrl: String = ""
)
