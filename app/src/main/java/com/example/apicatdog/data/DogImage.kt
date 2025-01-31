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
    @SerialName("favourite")
    val favourite: Favourite? = null,
    @SerialName("temperament")
    val temperament: String? = null,
    @SerialName("bred_for")
    val bred_for: String? = null,
    @SerialName("breed_group")
    val breed_group: String? = null,
    @SerialName("life_span")
    val life_span: String? = null

)

@Serializable
data class BreedDog(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("description")
    val temperament: String,
    @SerialName("bred_for")
    val bred_for: String,
    @SerialName("breed_group")
    val breed_group: String,
    @SerialName("life_span")
    val life_span: String

)


@Serializable
data class FavouriteDog(
    @SerialName("id")
    val id: String,
    @SerialName("image_id")
    val image_id: String
)


