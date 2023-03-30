package com.example.githubuser.ui.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubuser.data.UserRepository
import com.example.githubuser.data.remote.response.User
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`

class FavouriteViewModelTest {

    private lateinit var userRepository: UserRepository
    private lateinit var favouriteViewModel: FavouriteViewModel

    private val dummy: LiveData<List<User>> = MutableLiveData(listOf(User(1, "cakrapand", null)))

    @Before
    fun before() {
        userRepository = Mockito.mock(UserRepository::class.java)
        favouriteViewModel = FavouriteViewModel(userRepository)
    }

    @Test
    fun getFavouriteUser() {
        `when`(favouriteViewModel.getFavouriteUser()).thenReturn(dummy)
        val listFav = favouriteViewModel.getFavouriteUser()
        Mockito.verify(userRepository).getFavouriteUser()
        assertEquals(dummy.value, listFav.value)
    }
}