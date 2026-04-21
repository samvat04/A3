package com.example.sdangol1_a3.data

data class CastMember(
    val imdbPersonId: String,
    val name: String,
    val job: String,
    val characters: List<String> = emptyList(),
    val imdbUrl: String = ""
)
