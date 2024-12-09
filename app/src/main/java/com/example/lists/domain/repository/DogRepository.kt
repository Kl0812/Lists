package com.example.lists.domain.repository

import com.example.lists.data.remote.dto.DogDetailDto
import com.example.lists.data.remote.dto.DogDto

interface DogRepository {

    suspend fun getDogs(page: Int): List<DogDto>

    suspend fun getDogById(dogId: String): DogDetailDto

}