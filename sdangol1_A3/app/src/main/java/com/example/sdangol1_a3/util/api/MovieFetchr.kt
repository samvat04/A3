package com.example.sdangol1_a3.util.api

import com.example.sdangol1_a3.data.CastMember
import com.example.sdangol1_a3.data.SearchMovie
import com.example.sdangol1_a3.util.Constants
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

class MovieFetchr {
    // replace later with secrets plugin or BuildConfig value
    private val apiKey = "PUT_YOUR_RAPID_API_KEY_HERE"

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
        return movieApiService.autocomplete(query).d.map {
            SearchMovie(
                imdbId = it.id,
                title = it.l,
                description = it.q,
                year = it.y?.toString() ?: "",
                imageUrl = it.i?.imageUrl ?: ""
            )
        }
    }

    suspend fun fetchCast(imdbId: String): List<CastMember> {
        return movieApiService.getCast(imdbId).cast.map {
            CastMember(
                imdbPersonId = it.nconst,
                name = it.name,
                job = it.category,
                characters = it.characters
            )
        }
    }
}
