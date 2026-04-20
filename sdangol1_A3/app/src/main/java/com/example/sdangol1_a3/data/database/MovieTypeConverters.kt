package com.example.sdangol1_a3.data.database

import androidx.room.TypeConverter
import java.util.UUID

class MovieTypeConverters {
    @TypeConverter
    fun fromUUID(uuid: UUID?): String? = uuid?.toString()

    @TypeConverter
    fun toUUID(value: String?): UUID? = value?.let(UUID::fromString)

    @TypeConverter
    fun fromStringList(list: List<String>): String = list.joinToString("|")

    @TypeConverter
    fun toStringList(value: String?): List<String> =
        value?.takeIf { it.isNotBlank() }?.split("|") ?: emptyList()
}
