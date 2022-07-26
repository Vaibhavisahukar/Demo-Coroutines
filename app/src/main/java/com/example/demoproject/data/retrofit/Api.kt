package com.example.demoproject.data.retrofit

import com.example.demoproject.data.model.ResultsItem
import com.example.demoproject.data.model.User
import retrofit2.Response
import retrofit2.http.GET

interface Api {

    //implementation of this method will be provided by retrofit
    @GET("?results=20")
    suspend fun getUserDetails() : Response<User>
}
