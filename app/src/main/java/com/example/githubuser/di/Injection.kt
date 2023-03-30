package com.example.githubuser.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.githubuser.data.UserRepository
import com.example.githubuser.data.local.room.UserDatabase
import com.example.githubuser.data.remote.retrofit.ApiConfig

object Injection {

    fun provideRepository(context: Context, dataStore: DataStore<Preferences>? = null): UserRepository {
        val apiService = ApiConfig.getApiService()
        val database = UserDatabase.getInstance(context)
        val dao = database.userDao()
        return UserRepository.getInstance(apiService, dao, dataStore)
    }
}