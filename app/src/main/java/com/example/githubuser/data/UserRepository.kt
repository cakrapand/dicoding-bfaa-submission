package com.example.githubuser.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.*
import com.example.githubuser.data.local.room.UserDao
import com.example.githubuser.data.remote.response.SearchResponse
import com.example.githubuser.data.remote.response.User
import com.example.githubuser.data.remote.response.UserDetail
import com.example.githubuser.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.map
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository private constructor(private val apiService: ApiService, private val userDao: UserDao, private val dataStore: DataStore<Preferences>? = null) {

    private val THEME_KEY = booleanPreferencesKey("theme_setting")
//    private val result = MediatorLiveData<UserResult<List<User>>>()

    fun searchUser(query: String): LiveData<UserResult<List<User>>> = liveData {
        emit(UserResult.Loading)
        try{
            val response = apiService.searchUsers(query)
            val listUser = response.items
            emit(UserResult.Success(listUser))
        } catch (e: Exception){
            Log.d("UserRepository", "searchUser: ${e.message.toString()} ")
            emit(UserResult.Error(e.message.toString()))
        }
    }

//    fun searchUser(query: String = "a"): LiveData<UserResult<List<User>>>{
//        result.value = UserResult.Loading
//        val client = apiService.searchUsers(query)
//        client.enqueue(object: Callback<SearchResponse> {
//            override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
//                if(response.isSuccessful && response.body() != null){
//                    result.value = UserResult.Success(response.body()!!.items)
//                }else{
//                    result.value = UserResult.Error(response.message())
//                }
//            }
//            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
//                result.value = UserResult.Error(t.message)
//            }
//        })
//        return result
//    }

    fun getUserDetail(login: String): LiveData<UserResult<UserDetail>> = liveData {
        emit(UserResult.Loading)
        try{
            val response = apiService.getUserDetail(login)
            emit(UserResult.Success(response))
        } catch (e: Exception){
            Log.d("UserRepository", "getUserDetail: ${e.message.toString()} ")
            emit(UserResult.Error(e.message.toString()))
        }
    }

    fun getUserFollower(login: String): LiveData<UserResult<List<User>>> = liveData {
        emit(UserResult.Loading)
        try{
            val response = apiService.getUserFollowers(login)
            emit(UserResult.Success(response))
        } catch (e: Exception){
            Log.d("UserRepository", "getUserFollower: ${e.message.toString()} ")
            emit(UserResult.Error(e.message.toString()))
        }
    }

    fun getUserFollowing(login: String): LiveData<UserResult<List<User>>> = liveData {
        emit(UserResult.Loading)
        try{
            val response = apiService.getUserFollowing(login)
            emit(UserResult.Success(response))
        } catch (e: Exception){
            Log.d("UserRepository", "getUserFollowing: ${e.message.toString()} ")
            emit(UserResult.Error(e.message.toString()))
        }
    }

    fun getFavouriteUser(): LiveData<List<User>> {
        return userDao.getUsers()
    }

    suspend fun addFavouriteUser(user: User): Long{
        return userDao.addFavouriteUser(user)
    }

    suspend fun deleteFavouriteUser(login: String): Int{
        return userDao.deleteFavouriteUser(login)
    }

    fun getFavoriteUserByLogin(login: String): LiveData<User> = userDao.getFavoriteUserByLogin(login)


    fun getThemeSetting(): LiveData<Boolean> {
        return dataStore!!.data.map { preferences ->
            preferences[THEME_KEY] ?: false
        }.asLiveData()
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        dataStore!!.edit { preferences ->
            preferences[THEME_KEY] = isDarkModeActive
        }
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService,
            userDao: UserDao,
            dataStore: DataStore<Preferences>? = null
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, userDao, dataStore)
            }.also { instance = it }
    }

}