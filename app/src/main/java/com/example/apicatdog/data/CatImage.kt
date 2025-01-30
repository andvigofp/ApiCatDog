package com.example.apicatdog.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class CatImage(
    @SerialName("id")
    val id: String,
    @SerialName("url")
    val url: String,
    @SerialName("width")
    val width: Int,
    @SerialName("height")
    val height: Int,
    @SerialName("breeds")
    val breeds: List<Breed> = emptyList(),  // Proporcionar un valor predeterminado para manejar casos en los que no haya razas
    @SerialName("favourite")
    val favourite: Favourite? = null,
    @SerialName("description")
    val description: String? = null  // Asegúrate de que este campo esté presente
)

@Serializable
data class Breed(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("description")
    val description: String  // Asegúrate de que este campo esté presente
)

@Serializable
data class Favourite(
    @SerialName("id")
    val id: String,
    @SerialName("image_id")
    val image_id: String
)


