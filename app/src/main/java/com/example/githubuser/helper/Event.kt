package com.example.githubuser.helper

import android.util.Log

open class Event<out T>(private val content: T) {

    @Suppress("MemberVisibilityCanBePrivate")
    var hasBeenHandled = false
        private set

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            Log.i("TEST", "getContentIfNotHandled: TRUE")
            hasBeenHandled = true
            content
        }
    }

    fun peekContent(): T = content
}