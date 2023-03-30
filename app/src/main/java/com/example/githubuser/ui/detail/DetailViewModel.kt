package com.example.githubuser.ui.detail

import androidx.lifecycle.*
import com.example.githubuser.data.UserRepository
import com.example.githubuser.data.UserResult
import com.example.githubuser.data.remote.response.User
import com.example.githubuser.data.remote.response.UserDetail
import kotlinx.coroutines.launch

class DetailViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _userDetail = MutableLiveData<UserResult<UserDetail>>()
    val userDetail : LiveData<UserResult<UserDetail>>
        get() = _userDetail

    private val observerUserDetail = Observer<UserResult<UserDetail>> {
        _userDetail.value = it
    }

    private val _listFollower = MutableLiveData<UserResult<List<User>>>()
    val listFollower : LiveData<UserResult<List<User>>>
        get() = _listFollower

    private val observerFollower = Observer<UserResult<List<User>>> {
        _listFollower.value = it
    }

    private val _listFollowing = MutableLiveData<UserResult<List<User>>>()
    val listFollowing : LiveData<UserResult<List<User>>>
        get() = _listFollowing

    private val observerFollowing = Observer<UserResult<List<User>>> {
        _listFollowing.value = it
    }

    fun getUser(login: String){
        getUserDetail(login)
        getUserFollower(login)
        getUserFollowing(login)
    }
    private fun getUserDetail(login: String){
        LOGIN = login
        userRepository.getUserDetail(login).observeForever(observerUserDetail)
    }

    private fun getUserFollower(login: String) {
        LOGIN = login
        userRepository.getUserFollower(login).observeForever(observerFollower)
    }

    private fun getUserFollowing(login: String) {
        LOGIN = login
        userRepository.getUserFollowing(login).observeForever(observerFollowing)
    }

    fun addFavouriteUser(user: User){
        viewModelScope.launch {
            userRepository.addFavouriteUser(user)
        }
    }

    fun deleteFavouriteUser(login: String){
        viewModelScope.launch {
            userRepository.deleteFavouriteUser(login)
        }
    }

    fun getFavoriteUserByLogin(login: String) = userRepository.getFavoriteUserByLogin(login)

    override fun onCleared() {
        super.onCleared()
        userRepository.getUserDetail(LOGIN).removeObserver(observerUserDetail)
        userRepository.getUserFollower(LOGIN).removeObserver(observerFollower)
        userRepository.getUserFollowing(LOGIN).removeObserver(observerFollowing)
    }

    companion object{
        private var LOGIN = ""
    }

}