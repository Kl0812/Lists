package com.example.lists.data.remote.dto

import com.example.lists.domain.model.DogDetail

data class DogDetailDto(
    val breeds: List<Breed>,
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
)

fun DogDetailDto.toDogDetail(): DogDetail {
    return DogDetail(
        dogId = id,
        dogUrl = url,
        dogBreed = breeds.joinToString(", ") { it.name },
        dogOrigin = breeds.joinToString(", ") { it.origin ?: "No Information" } ,
        description = breeds.firstOrNull()?.description ?: "No Information",
        adaptability = breeds.firstOrNull()?.adaptability ?: -1,
        affectionLevel = breeds.firstOrNull()?.affection_level ?: -1,
        childFriendly = breeds.firstOrNull()?.child_friendly ?: -1,
        strangerFriendly = breeds.firstOrNull()?.stranger_friendly ?: -1,
        socialNeeds = breeds.firstOrNull()?.social_needs ?: -1,
        energyLevel = breeds.firstOrNull()?.energy_level ?: -1
    )
}
