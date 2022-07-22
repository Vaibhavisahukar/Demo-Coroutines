package com.example.demoproject.data.retrofit

import com.example.demoproject.data.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    //implementation of this method will be provided by retrofit
    @GET("?results=20")
    //suspend fun getUserDetails() : Response<com.example.demoproject.data.model.User>
    //suspend fun getUserDetails(@Query("page")page: Int) : User
    suspend fun getUserDetails() : User
}
