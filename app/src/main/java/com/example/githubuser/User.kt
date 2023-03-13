package com.example.githubuser

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(

    @field:SerializedName("id")
    val id: Long,

    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("avatar_url")
    val avatar_url: String
    ): Parcelable

data class SearchResponse(

    @field:SerializedName("total_count")
    val total_count: Long,

    @field:SerializedName("incomplete_results")
    val incomplete_results: Boolean,

    @field:SerializedName("items")
    val items: List<User>
)

data class UserDetail(

    @field:SerializedName("id")
    val id: Long,

    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("avatar_url")
    val avatar_url: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("followers")
    val followers: Long,

    @field:SerializedName("following")
    val following: Long
)
