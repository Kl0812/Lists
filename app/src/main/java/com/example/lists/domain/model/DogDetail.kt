package com.example.lists.domain.model

data class DogDetail(
    val dogId: String,
    val dogUrl: String,
    val dogBreed: String,
    val dogOrigin: String,
    val description: String,
    val adaptability: Int,
    val affectionLevel: Int,
    val childFriendly: Int,
    val strangerFriendly: Int,
    val socialNeeds: Int,
    val energyLevel: Int
)
