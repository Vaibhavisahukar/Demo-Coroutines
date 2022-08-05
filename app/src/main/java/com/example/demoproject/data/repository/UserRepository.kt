package com.example.demoproject.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.demoproject.data.model.ResultsItem
import com.example.demoproject.data.model.User
import com.example.demoproject.data.retrofit.Api
import com.example.demoproject.database.DataDao
import javax.inject.Inject

class UserRepository @Inject constructor(private val api: Api, private val dataDao: DataDao) {

    private val _user = MutableLiveData<User>()

    val allUsers: LiveData<List<ResultsItem>> = dataDao.getData()

    suspend fun getUser() {
        val userResults = api.getUserDetails()
        if (userResults.isSuccessful && userResults.body() != null) {
            dataDao.insertData(userResults.body()!!.results)
            _user.postValue(userResults.body())
            Log.d("message", "response+$allUsers")
        }
    }

    suspend fun deleteUser(resultsItem: ResultsItem) = dataDao.delete(resultsItem)
    suspend fun deleteAllUser() = dataDao.deleteUsers()
}
