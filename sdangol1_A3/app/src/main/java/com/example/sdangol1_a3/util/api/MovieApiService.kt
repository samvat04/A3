package com.example.sdangol1_a3.util.api

import com.example.sdangol1_a3.data.response.AutocompleteResponse
import com.example.sdangol1_a3.data.response.CastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {
    @GET("auto-complete")
    suspend fun autocomplete(
        @Query("q") query: String
    ): AutocompleteResponse

    @GET("title/get-top-cast")
    suspend fun getCast(
        @Query("tconst") imdbId: String
    ): CastResponse
}
