package com.example.sdangol1_a3.data.database

import androidx.room.TypeConverter

class MovieTypeConverters {

    @TypeConverter
    fun fromGenres(genres: List<String>): String {
        return genres.joinToString(",")
    }

    @TypeConverter
    fun toGenres(genresString: String): List<String> {
        return if (genresString.isBlank()) emptyList()
        else genresString.split(",")
    }
}
