package com.example.apicatdog.model

import com.example.apicatdog.network.DogApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val loggingInterceptorDog = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

val httpClientDog = OkHttpClient.Builder()
    .addInterceptor(loggingInterceptorDog) // Cambiado a loggingInterceptorDog
    .build()

val retrofitDog = Retrofit.Builder()
    .baseUrl("https://api.thedogapi.com/") // URL de la API de perros
    .addConverterFactory(GsonConverterFactory.create())
    .client(httpClientDog)
    .build()

val dogApiService = retrofitDog.create(DogApiService::class.java)

