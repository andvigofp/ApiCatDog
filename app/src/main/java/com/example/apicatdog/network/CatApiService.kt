package com.example.apicatdog.network

import com.example.apicatdog.data.CatImage
import retrofit2.http.GET
import retrofit2.http.Query

interface CatApiService {
    @GET("v1/images/search")
    suspend fun getCatImages(
        @Query("limit") limit: Int = 50, // Ajuste el límite aquí
        @Query("page") page: Int = 0,
        @Query("order") order: String = "RAND",
        @Query("has_breeds") hasBreeds: Int = 0,
        @Query("breed_ids") breedIds: String? = null,
        @Query("category_ids") categoryIds: String? = null,
        @Query("sub_id") subId: String? = null
    ): List<CatImage>
}




