package com.example.lists.data.remote.dto

import com.example.lists.domain.model.Dog

data class DogDto(
    val height: Int,
    val id: String,
    val url: String,
    val width: Int,
    val dogBreed: String?
)

fun DogDto.toDog(): Dog {
    return Dog(
        id = id,
        url = url
    )
}