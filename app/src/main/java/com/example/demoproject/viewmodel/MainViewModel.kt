package com.example.demoproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demoproject.data.repository.UserRepository
import kotlinx.coroutines.launch

class MainViewModel(private val userRepository: UserRepository): ViewModel() {

    val userLiveData: LiveData<com.example.demoproject.data.model.User>
    get() = userRepository.user

    init {
        viewModelScope.launch {
            userRepository.getUser()
        }
    }
}
