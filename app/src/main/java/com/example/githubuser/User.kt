package com.example.githubuser

import com.google.gson.annotations.SerializedName

data class User(

    @field:SerializedName("id")
    val id: Long,

    @field:SerializedName("login")
    val login: String,

//    @field:SerializedName("name")
//    val name: String,
//
//    @field:SerializedName("followers")
//    val followers: Int,
//
//    @field:SerializedName("following")
//    val following: Int,

    @field:SerializedName("avatar_url")
    val avatar_url: String
    )
