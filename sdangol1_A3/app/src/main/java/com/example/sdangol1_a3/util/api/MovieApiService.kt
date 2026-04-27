package com.example.sdangol1_a3.util.api

import com.example.sdangol1_a3.data.response.ImdbCastDto
import com.example.sdangol1_a3.data.response.ImdbTitleDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {
    // Returns autocomplete title matches for the provided search query
    @GET("api/imdb/autocomplete")
    suspend fun autocomplete(
        @Query("query") query: String
    ): List<ImdbTitleDto>

    // Returns cast information for a specific IMDb title ID
    @GET("api/imdb/{imdbId}/cast")
    suspend fun getCast(
        @Path("imdbId") imdbId: String
    ): List<ImdbCastDto>
}
