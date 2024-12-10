package com.example.lists.domain.use_case.get_dogs

import com.example.lists.common.Resource
import com.example.lists.data.remote.dto.toDog
import com.example.lists.domain.model.Dog
import com.example.lists.domain.repository.DogRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import okio.IOException
import retrofit2.HttpException

import javax.inject.Inject

/*
* Set page loading state and set dog list information
* */
class GetDogsUseCase @Inject constructor(
    private val repository: DogRepository
) {
    operator fun invoke(page: Int): Flow<Resource<List<Dog>>> = flow {
        try{
            emit(Resource.Loading<List<Dog>>())
            val dogs = repository.getDogs(page).map {it.toDog()}
            emit(Resource.Success<List<Dog>>(dogs))
        } catch (e: HttpException) {
            emit(Resource.Error<List<Dog>>(e.localizedMessage ?: "An Unexpected Error Occur"))
        } catch (e: IOException) {
            emit(Resource.Error<List<Dog>>("Can not reach server, please check the internet"))
        }
    }
}