package com.example.githubuser

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    companion object{
        private const val TAG = "MainViewModel"
    }

    private val _listUser = MutableLiveData<List<User>>()
    val listUser : LiveData<List<User>>
        get() =_listUser

    init {
        getAllUsers()
    }

    private fun getAllUsers(){
        val client = ApiConfig.getApiService().getAllUsers()
        client.enqueue(object: Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if(response.isSuccessful && response.body() != null){
                    _listUser.value = response.body()
                }else{
                    Log.e(TAG, "onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}