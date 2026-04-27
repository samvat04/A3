package com.example.sdangol1_a3.data.response

import kotlinx.serialization.Serializable

// DTO used to deserialize title results directly from the IMDb API response
@Serializable
data class ImdbTitleDto(
    val id: String,
    val url: String? = null,
    val primaryTitle: String,
    val originalTitle: String? = null,
    val type: String? = null,
    val description: String? = null,
    val primaryImage: String? = null,
    val startYear: Int? = null,
    val genres: List<String>? = null,
    val averageRating: Double? = null
)
