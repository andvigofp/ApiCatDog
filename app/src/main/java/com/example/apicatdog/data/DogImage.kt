package com.example.apicatdog.data

data class DogImage(
    val id: String,
    val url: String,
    val width: Int,
    val height: Int,
    val breeds: List<Breed>,
    val favourite: Favourite?
)