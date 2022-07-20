package com.example.demoproject.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.demoproject.data.retrofit.Api
import javax.inject.Inject

class UserRepository @Inject constructor(private val api: Api) {

    private val _user = MutableLiveData<com.example.demoproject.data.model.User>()
    val user : LiveData<com.example.demoproject.data.model.User>
    get() = _user

    suspend fun getUser() {
        val userResults = api.getUserDetails()
        if (userResults.isSuccessful && userResults.body() != null) {
            _user.postValue(userResults.body())
        }
    }
}
