package com.example.demoproject.di

import com.example.demoproject.data.retrofit.Api
import com.example.demoproject.utils.Constants
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    //creates the retrofit object
    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {

        val gson = GsonBuilder()
            .setLenient()
            .create()
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    //provides implementation to the Api interface methods
    @Singleton
    @Provides
    fun providesApi(retrofit: Retrofit) : Api {
        return retrofit.create(Api::class.java)
    }
}
