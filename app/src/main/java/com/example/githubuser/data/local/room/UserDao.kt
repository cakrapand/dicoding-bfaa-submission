package com.example.githubuser.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.githubuser.data.remote.response.User

@Dao
interface UserDao {

    @Query("SELECT * FROM FavoriteUser")
    fun getUsers(): LiveData<List<User>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavouriteUser(user: User): Long

    @Query("DELETE FROM FavoriteUser WHERE login = :login")
    suspend fun deleteFavouriteUser(login: String): Int

    @Query("SELECT * FROM FavoriteUser WHERE login = :login")
    fun getFavoriteUserByLogin(login: String): LiveData<User>

}