package com.example.githubuser

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @Headers("Authorization: token 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")
    @GET("users")
    fun getUsers(): Call<List<User>>

    @Headers("Authorization: token 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")
    @GET("search/users")
    fun searchUsers(@Query("q") login: String): Call<SearchResponse>

    @Headers("Authorization: token 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")
    @GET("users/{login}")
    fun getUserDetail(@Path("login")login: String): Call<UserDetail>

    @Headers("Authorization: token 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")
    @GET("users/{login}/followers")
    fun getUserFollowers(@Path("login")login: String): Call<List<User>>

    @Headers("Authorization: token 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")
    @GET("users/{login}/following")
    fun getUserFollowing(@Path("login")login: String): Call<List<User>>

}