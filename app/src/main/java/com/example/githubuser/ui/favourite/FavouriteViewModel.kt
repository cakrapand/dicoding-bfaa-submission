package com.example.githubuser.ui.favourite

import androidx.lifecycle.ViewModel
import com.example.githubuser.data.UserRepository

class FavouriteViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getFavouriteUser() = userRepository.getFavouriteUser()
}