package com.example.lists.domain.use_case.get_dog

import com.example.lists.common.Resource
import com.example.lists.data.remote.dto.toDog
import com.example.lists.data.remote.dto.toDogDetail
import com.example.lists.domain.model.DogDetail
import com.example.lists.domain.repository.DogRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import okio.IOException
import retrofit2.HttpException

import javax.inject.Inject

class GetDogUseCase @Inject constructor(
    private val repository: DogRepository
) {
    operator fun invoke(dogId: String): Flow<Resource<DogDetail>> = flow {
        try{
            emit(Resource.Loading<DogDetail>())
            val dog = repository.getDogById(dogId).toDogDetail()
            emit(Resource.Success<DogDetail>(dog))
        } catch (e: HttpException) {
            emit(Resource.Error<DogDetail>(e.localizedMessage ?: "An Unexpected Error Occur"))
        } catch (e: IOException) {
            emit(Resource.Error<DogDetail>("Can not reach server, please check the internet"))
        }
    }
}