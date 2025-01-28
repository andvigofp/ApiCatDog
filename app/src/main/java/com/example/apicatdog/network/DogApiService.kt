package com.example.apicatdog.network

import com.example.apicatdog.data.DogImage
import retrofit2.http.GET
import retrofit2.http.Query

interface DogApiService {
    @GET("v1/images/search")
    suspend fun getDogImages(
        @Query("limit") limit: Int = 50,
        @Query("page") page: Int = 0,
        @Query("order") order: String = "RAND",
        @Query("has_breeds") hasBreeds: Int = 0,
        @Query("breed_ids") breedIds: String? = null,
        @Query("category_ids") categoryIds: String? = null,
        @Query("sub_id") subId: String? = null
    ): List<DogImage>
}
