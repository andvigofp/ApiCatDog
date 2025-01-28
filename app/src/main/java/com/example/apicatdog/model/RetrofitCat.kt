package com.example.apicatdog.model

import com.example.apicatdog.network.CatApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

val loggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

val httpClient = OkHttpClient.Builder()
    .addInterceptor(loggingInterceptor)
    .build()

val retrofitCat = Retrofit.Builder()
    .baseUrl("https://api.thecatapi.com/")
    .addConverterFactory(GsonConverterFactory.create())
    .client(httpClient)
    .build()

val catApiService = retrofitCat.create(CatApiService::class.java)



