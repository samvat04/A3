package com.example.sdangol1_a3.util.api

import okhttp3.Interceptor
import okhttp3.Response

class RapidApiInterceptor(
    private val apiKey: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("X-RapidAPI-Key", apiKey)
            .addHeader("X-RapidAPI-Host", "imdb8.p.rapidapi.com")
            .build()

        return chain.proceed(request)
    }
}
