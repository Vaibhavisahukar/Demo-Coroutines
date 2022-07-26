package com.example.demoproject.data.repository

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.demoproject.data.model.ResultsItem
import com.example.demoproject.data.model.User
import com.example.demoproject.data.retrofit.Api
import com.example.demoproject.database.DataDao
import com.example.demoproject.database.UserDatabase
import javax.inject.Inject

class UserRepository @Inject constructor(private val api: Api,val dataDao: DataDao) {

    private val _user = MutableLiveData<User>()

    val allUsers: LiveData<List<ResultsItem>> = dataDao.getData()

    suspend fun getUser() {
        val userResults = api.getUserDetails()
        if (userResults.isSuccessful && userResults.body() != null) {
            dataDao.insertData(userResults.body()!!.results as ArrayList<ResultsItem>)
            _user.postValue(userResults.body())
        }
    }

    suspend fun deleteUser(resultsItem: ResultsItem) = dataDao.delete(resultsItem)
}
