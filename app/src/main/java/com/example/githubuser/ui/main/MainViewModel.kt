package com.example.githubuser.ui.main

import androidx.lifecycle.*
import com.example.githubuser.data.UserRepository
import com.example.githubuser.data.UserResult
import com.example.githubuser.data.remote.response.User
import kotlinx.coroutines.launch

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _userResult = MutableLiveData<UserResult<List<User>>>()
    val userResult : LiveData<UserResult<List<User>>>
        get() = _userResult

    private val observer = Observer<UserResult<List<User>>> {
        _userResult.value = it
    }

    init{
        searchUser()
    }

    fun searchUser(query: String = "a"){
        QUERY = query
        userRepository.searchUser(query).observeForever(observer)
    }

    fun getThemeSetting() = userRepository.getThemeSetting()

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            userRepository.saveThemeSetting(isDarkModeActive)
        }
    }

    override fun onCleared() {
        super.onCleared()
        userRepository.searchUser(QUERY).removeObserver(observer)
    }

    companion object{
        private var QUERY = ""
    }
}