package com.example.apicatdog.data

data class CatImage(
    val id: String,
    val url: String,
    val width: Int,
    val height: Int,
    val breeds: List<Breed>,
    val favourite: Favourite?
)

data class Breed(
    val id: String,
    val name: String,
    val description: String
)

data class Favourite(
    val id: String,
    val image_id: String
)
