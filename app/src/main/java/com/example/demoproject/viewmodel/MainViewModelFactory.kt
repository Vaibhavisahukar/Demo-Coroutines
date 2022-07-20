package com.example.demoproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.demoproject.data.repository.UserRepository
import javax.inject.Inject

class MainViewModelFactory @Inject constructor(private val userRepository: UserRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(userRepository) as T
    }
}
