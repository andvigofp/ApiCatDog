package com.example.apicatdog.model

import com.example.apicatdog.data.CatImage
import com.example.apicatdog.network.CatApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

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