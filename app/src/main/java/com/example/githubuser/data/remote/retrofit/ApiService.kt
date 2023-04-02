package com.example.githubuser.data.remote.retrofit

import com.example.githubuser.data.remote.response.SearchResponse
import com.example.githubuser.data.remote.response.User
import com.example.githubuser.data.remote.response.UserDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    suspend fun searchUsers(@Query("q") login: String): SearchResponse

    @GET("users/{login}")
    suspend fun getUserDetail(@Path("login")login: String): UserDetail

    @GET("users/{login}/followers")
    suspend fun getUserFollowers(@Path("login")login: String): List<User>

    @GET("users/{login}/following")
    suspend fun getUserFollowing(@Path("login")login: String): List<User>

}