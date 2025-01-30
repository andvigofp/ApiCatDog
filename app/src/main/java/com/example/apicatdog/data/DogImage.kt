package com.example.apicatdog.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DogImage(
    @SerialName("id")
    val id: String,
    @SerialName("url")
    val url: String,
    @SerialName("width")
    val width: Int,
    @SerialName("height")
    val height: Int,
    @SerialName("breeds")
    val breeds: List<BreedDog> = emptyList(),
    @SerialName("description")
    val description: String? = null
)

@Serializable
data class BreedDog(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("description")
    val description: String
)


@Serializable
data class FavouriteDog(
    @SerialName("id")
    val id: String,
    @SerialName("image_id")
    val image_id: String
)


