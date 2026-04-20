package com.example.sdangol1_a3.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.sdangol1_a3.data.Movie
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface MovieDao {
    @Insert
    suspend fun addMovie(movie: Movie)

    @Update
    suspend fun updateMovie(movie: Movie)

    @Delete
    suspend fun deleteMovie(movie: Movie): Int

    @Query("SELECT * FROM movie")
    fun getMovies(): Flow<List<Movie>>

    @Query("SELECT * FROM movie WHERE id = (:id)")
    suspend fun getMovieById(id: UUID): Movie?

    @Query("SELECT * FROM movie WHERE imdbId = (:imdbId) LIMIT 1")
    suspend fun getMovieByImdbId(imdbId: String): Movie?
}
