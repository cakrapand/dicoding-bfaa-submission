package com.example.githubuser.data.remote.response

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "FavoriteUser")
@Parcelize
data class User(

    @field:ColumnInfo(name = "Id")
    @field:PrimaryKey
    @field:SerializedName("id")
    val id: Long,

    @field:ColumnInfo(name = "Login")
    @field:SerializedName("login")
    val login: String,

    @field:ColumnInfo(name = "AvatarUrl")
    @field:SerializedName("avatar_url")
    val avatarUrl: String? = null
    ): Parcelable

data class SearchResponse(
    @field:SerializedName("items")
    val items: List<User>
)

data class UserDetail(

    @field:SerializedName("id")
    val id: Long,

    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("url")
    val url: String,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("followers")
    val followers: Long,

    @field:SerializedName("following")
    val following: Long
)
