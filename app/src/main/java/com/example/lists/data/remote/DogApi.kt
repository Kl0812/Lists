package com.example.lists.data.remote

import com.example.lists.common.Constants
import com.example.lists.data.remote.dto.DogDetailDto
import com.example.lists.data.remote.dto.DogDto
import retrofit2.http.GET
import retrofit2.http.Path

interface DogApi {

    //Only get 20 dogs once
    @GET("/v1/images/search?limit=20&has_breeds=1&api_key=${Constants.API_KEY}")
    suspend fun getDogs(): List<DogDto>

    @GET("/v1/images/{dogId}")
    suspend fun getDogById(@Path("dogId") dogId: String): DogDetailDto

}