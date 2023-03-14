package com.example.githubuser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel() : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user : LiveData<User>
        get() =_user

    private val _userDetail = MutableLiveData<UserDetail>()
    val userDetail : LiveData<UserDetail>
        get() =_userDetail

    private val _userFollower = MutableLiveData<List<User>>()
    val userFollower : LiveData<List<User>>
        get() =_userFollower

    private val _userFollowing = MutableLiveData<List<User>>()
    val userFollowing : LiveData<List<User>>
        get() =_userFollowing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean>
        get() =_isLoading

    private val _isLoadingFollow = MutableLiveData<Boolean>()
    val isLoadingFollow : LiveData<Boolean>
        get() =_isLoadingFollow

    companion object{
        private const val TAG = "DetailViewModel"
    }

    init {

    }

    fun getUser(login: String){
        getUserDetail(login)
        getUserFollower(login)
        getUserFollowing(login)
    }

    fun getUserDetail(login: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserDetail(login)
        client.enqueue(object: Callback<UserDetail> {
            override fun onResponse(call: Call<UserDetail>, response: Response<UserDetail>) {
                _isLoading.value = false
                if(response.isSuccessful && response.body() != null){
                    _userDetail.value = response.body()
                }else{
                    Log.e(TAG, "onResponse: ${response}")
                }
            }
            override fun onFailure(call: Call<UserDetail>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getUserFollower(login: String){
        _isLoadingFollow.value = true
        val client = ApiConfig.getApiService().getUserFollowers(login)
        client.enqueue(object: Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                _isLoadingFollow.value = false
                if(response.isSuccessful && response.body() != null){
                    _userFollower.value = response.body()
                }else{
                    Log.e(TAG, "onResponse: ${response}")
                }
            }
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                _isLoadingFollow.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getUserFollowing(login: String){
        _isLoadingFollow.value = true
        val client = ApiConfig.getApiService().getUserFollowing(login)
        client.enqueue(object: Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                _isLoadingFollow.value = false
                if(response.isSuccessful && response.body() != null){
                    _userFollowing.value = response.body()
                }else{
                    Log.e(TAG, "onResponse: ${response}")
                }
            }
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                _isLoadingFollow.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}