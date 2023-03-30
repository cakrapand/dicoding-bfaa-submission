package com.example.githubuser.data

import com.example.githubuser.helper.Event

sealed class UserResult<out R> private constructor() {
    data class Success<out T>(val data: T) : UserResult<T>()
    data class Error(val error: Event<String>?) : UserResult<Nothing>()
    object Loading : UserResult<Nothing>()
}