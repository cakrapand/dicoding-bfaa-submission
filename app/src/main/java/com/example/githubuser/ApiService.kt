package com.example.githubuser

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface ApiService {
    @Headers("Authorization: token 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")
    @GET("users")
    fun getAllUsers(): Call<List<User>>
}