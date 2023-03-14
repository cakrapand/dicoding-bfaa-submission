package com.example.githubuser

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    fun searchUsers(@Query("q") login: String): Call<SearchResponse>

    @GET("users/{login}")
    fun getUserDetail(@Path("login")login: String): Call<UserDetail>

    @GET("users/{login}/followers")
    fun getUserFollowers(@Path("login")login: String): Call<List<User>>

    @GET("users/{login}/following")
    fun getUserFollowing(@Path("login")login: String): Call<List<User>>

}