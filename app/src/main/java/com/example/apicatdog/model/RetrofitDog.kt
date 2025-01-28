package com.example.apicatdog.model

import com.example.apicatdog.network.DogApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val retrofitDog = Retrofit.Builder()
    .baseUrl("https://api.thedogapi.com/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val dogApiService = retrofitDog.create(DogApiService::class.java)