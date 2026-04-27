package com.example.sdangol1_a3.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "movie")
data class Movie(
    // UUID is used as the local primary key so each saved movie has a database identity
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    val imdbId: String,
    val title: String,
    val description: String,
    val year: String,
    val imageUrl: String = "",
    val averageRating: String = "",
    val genres: List<String> = emptyList(),
    val isFavorite: Boolean = false
)
