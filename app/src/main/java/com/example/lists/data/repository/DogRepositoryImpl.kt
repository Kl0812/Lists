package com.example.lists.data.repository

import com.example.lists.data.remote.DogApi
import com.example.lists.data.remote.dto.DogDetailDto
import com.example.lists.data.remote.dto.DogDto
import com.example.lists.domain.repository.DogRepository
import javax.inject.Inject

class DogRepositoryImpl @Inject constructor(
    private val api: DogApi
) : DogRepository {

    override suspend fun getDogs(): List<DogDto> {
        return api.getDogs()
    }

    override suspend fun getDogById(dogId: String): DogDetailDto {
        return api.getDogById(dogId)
    }
}