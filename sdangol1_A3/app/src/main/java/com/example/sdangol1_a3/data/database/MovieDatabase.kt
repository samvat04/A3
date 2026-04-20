package com.example.sdangol1_a3.data.database

import android.annotation.SuppressLint
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.sdangol1_a3.data.Movie

@SuppressLint("RestrictedApi")
@Database(
    entities = [Movie::class],
    version = 1
)
@TypeConverters(MovieTypeConverters::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract val movieDao: MovieDao

    companion object {
        private var INSTANCE: MovieDatabase? = null

        fun getInstance(context: Context): MovieDatabase {
            return INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                MovieDatabase::class.java,
                "movie-database"
            ).build().also {
                INSTANCE = it
            }
        }
    }
}
