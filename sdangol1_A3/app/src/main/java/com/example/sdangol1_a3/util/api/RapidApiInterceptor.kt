package com.example.sdangol1_a3.util.api

import com.example.sdangol1_a3.util.Constants
import okhttp3.Interceptor
import okhttp3.Response

class RapidApiInterceptor(
    private val apiKey: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        // Attaches the required RapidAPI headers to every outgoing request
        val request = chain.request()
            .newBuilder()
            .addHeader("x-rapidapi-key", apiKey)
            .addHeader("x-rapidapi-host", Constants.RAPID_API_HOST)
            .addHeader("Content-Type", "application/json")
            .build()

        return chain.proceed(request)
    }
}
