package com.example.sdangol1_a3.data

import android.content.Context
import com.example.sdangol1_a3.BuildConfig
import com.example.sdangol1_a3.data.database.MovieDatabase
import com.example.sdangol1_a3.util.api.MovieFetchr
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class MovieRepo private constructor(
    private val fetchr: MovieFetchr,
    private val database: MovieDatabase
) {
    private val movieDao = database.movieDao

    companion object {
        private var INSTANCE: MovieRepo? = null

        fun getInstance(context: Context): MovieRepo {
            android.util.Log.d(
                "API_KEY_CHECK",
                "Key length = ${BuildConfig.RAPID_API_KEY.length}"
            )
            // Keeps a single shared repository instance
            return INSTANCE ?: MovieRepo(
                fetchr = MovieFetchr(BuildConfig.RAPID_API_KEY),
                database = MovieDatabase.getInstance(context)
            ).also { INSTANCE = it }
        }
    }

    // Exposes the saved movie list as a Flow so UI state updates automatically when the database changes
    fun getMovies(): Flow<List<Movie>> = movieDao.getMovies()

    suspend fun loadMovie(id: UUID): Movie? = movieDao.getMovieById(id)

    suspend fun addMovie(movie: Movie) = movieDao.addMovie(movie)

    suspend fun updateMovie(movie: Movie) = movieDao.updateMovie(movie)

    suspend fun deleteMovie(movie: Movie): Boolean = movieDao.deleteMovie(movie) > 0

    // Used before inserting a movie to avoid duplicate saves for the same IMDb entry
    suspend fun alreadyExists(imdbId: String): Boolean =
        movieDao.getMovieByImdbId(imdbId) != null

    suspend fun searchMovies(query: String): List<SearchMovie> =
        fetchr.searchMovies(query)

    suspend fun fetchCast(imdbId: String): List<CastMember> =
        fetchr.fetchCast(imdbId)
}
