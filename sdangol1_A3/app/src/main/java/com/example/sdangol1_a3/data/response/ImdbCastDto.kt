package com.example.sdangol1_a3.data.response

import kotlinx.serialization.Serializable

// DTO used to deserialize cast member results directly from the IMDb API response
@Serializable
data class ImdbCastDto(
    val id: String,
    val url: String? = null,
    val fullName: String,
    val primaryImage: String? = null,
    val job: String,
    val characters: List<String> = emptyList()
)
