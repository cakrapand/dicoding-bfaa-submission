package com.example.githubuser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _listUsers = MutableLiveData<List<User>>()
    val listUsers : LiveData<List<User>>
        get() =_listUsers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean>
        get() =_isLoading

    private val _isError = MutableLiveData<Event<Boolean>>()
    val isError : LiveData<Event<Boolean>>
        get() =_isError

    init {
        searchUser()
    }

    fun searchUser(query: String = "a"){
        _isLoading.value = true
        val client = ApiConfig.getApiService().searchUsers(query)
        client.enqueue(object: Callback<SearchResponse> {
            override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                _isLoading.value = false
                if(response.isSuccessful && response.body() != null){
                    _isError.value = Event(false)
                    _listUsers.value = response.body()!!.items
                }else{
                    _isError.value = Event(true)
                    Log.e(TAG, "onResponse: $response")
                }
            }
            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                _isLoading.value = false
                _isError.value = Event(true)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object{
        private const val TAG = "MainViewModel"
    }
}