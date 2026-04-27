package com.example.sdangol1_a3.util.api

import com.example.sdangol1_a3.data.CastMember
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
    // Unknown JSON fields are ignored so small API response changes do not break parsing
    private val json = Json {
        ignoreUnknownKeys = true
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(
            // The interceptor injects the RapidAPI authentication headers for every request
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
            // Filters out non-movie matches so autocomplete only shows movie results
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

    suspend fun fetchCast(imdbId: String): List<CastMember> {
        return movieApiService.getCast(imdbId).map { dto ->
            CastMember(
                imdbPersonId = dto.id,
                name = dto.fullName,
                job = dto.job,
                characters = dto.characters,
                // Falls back to a constructed IMDb URL if the API does not provide one
                imdbUrl = dto.url ?: "https://www.imdb.com/name/${dto.id}/"
            )
        }
    }
}
