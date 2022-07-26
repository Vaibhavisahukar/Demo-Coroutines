package com.example.demoproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demoproject.data.model.ResultsItem
import com.example.demoproject.data.model.User
import com.example.demoproject.data.repository.UserRepository
import com.example.demoproject.database.DataDao
import com.example.demoproject.database.UserDatabase
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel() {

    val allUsers : LiveData<List<ResultsItem>> = userRepository.allUsers

    init {
        viewModelScope.launch {
            userRepository.getUser()
        }
    }

    fun deleteUser(resultsItem: ResultsItem) = viewModelScope.launch {
        userRepository.deleteUser(resultsItem)
    }
}
