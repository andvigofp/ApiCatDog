package com.example.apicatdog.model

import android.app.Application
import com.example.apicatdog.network.CatApiService
import com.example.apicatdog.network.DogApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiCatDog : Application() {

    // Retrofit instance for Cat API
    private val retrofitCat by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.thecatapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Retrofit instance for Dog API
    private val retrofitDog by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.thedogapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Service instance for Cat API
    val catApiService: CatApiService by lazy {
        retrofitCat.create(CatApiService::class.java)
    }

    // Service instance for Dog API
    val dogApiService: DogApiService by lazy {
        retrofitDog.create(DogApiService::class.java)
    }
}
