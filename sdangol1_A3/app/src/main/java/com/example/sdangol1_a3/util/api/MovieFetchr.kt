package com.example.sdangol1_a3.util.api

import com.example.sdangol1_a3.data.CastMember
import com.example.sdangol1_a3.data.Movie
import com.example.sdangol1_a3.data.SearchMovie
import com.example.sdangol1_a3.util.Constants
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

class MovieFetchr(
    apiKey: String
) {
    private val json = Json {
        ignoreUnknownKeys = true
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(
            OkHttpClient.Builder()
                .addInterceptor(RapidApiInterceptor(apiKey))
                .build()
        )
        .addConverterFactory(
            json.asConverterFactory("application/json".toMediaType())
        )
        .build()

    private val movieApiService = retrofit.create(MovieApiService::class.java)

    suspend fun searchMovies(query: String): List<SearchMovie> {
        return movieApiService.autocomplete(query)
            .filter { it.type == "movie" }
            .map { dto ->
                SearchMovie(
                    imdbId = dto.id,
                    title = dto.primaryTitle,
                    description = dto.description ?: "",
                    year = dto.startYear?.toString() ?: "",
                    imageUrl = dto.primaryImage ?: "",
                    averageRating = dto.averageRating?.toString() ?: "",
                    genres = dto.genres ?: emptyList()
                )
            }
    }

    suspend fun fetchMovieDetails(imdbId: String): Movie {
        val dto = movieApiService.autocomplete(imdbId).firstOrNull { it.id == imdbId }
            ?: throw IllegalArgumentException("Movie not found")

        return Movie(
            imdbId = dto.id,
            title = dto.primaryTitle,
            description = dto.description ?: "",
            year = dto.startYear?.toString() ?: "",
            imageUrl = dto.primaryImage ?: "",
            averageRating = dto.averageRating?.toString() ?: "",
            genres = dto.genres ?: emptyList()
        )
    }

    suspend fun fetchCast(imdbId: String): List<CastMember> {
        return movieApiService.getCast(imdbId).map { dto ->
            CastMember(
                imdbPersonId = dto.id,
                name = dto.fullName,
                job = dto.job,
                characters = dto.characters,
                imdbUrl = dto.url ?: "https://www.imdb.com/name/${dto.id}/"
            )
        }
    }
}
