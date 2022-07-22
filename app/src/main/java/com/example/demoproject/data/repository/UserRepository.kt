package com.example.demoproject.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.demoproject.data.retrofit.Api
import com.example.demoproject.paging.UserPagingSource
import javax.inject.Inject

class UserRepository @Inject constructor(private val api: Api) {

    /*private val _user = MutableLiveData<com.example.demoproject.data.model.User>()
    val user : LiveData<com.example.demoproject.data.model.User>
    get() = _user

    suspend fun getUser() {
        val userResults = api.getUserDetails()
        if (userResults.isSuccessful && userResults.body() != null) {
            _user.postValue(userResults.body())
        }
    }*/

    fun getUser() = Pager(
        config = PagingConfig(pageSize = 2, maxSize = 20),
        pagingSourceFactory = { UserPagingSource(api) }
    ).liveData
}
