package com.example.lists.data.remote

import com.example.lists.common.Constants
import com.example.lists.data.remote.dto.DogDetailDto
import com.example.lists.data.remote.dto.DogDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/*
* API information
* */
interface DogApi {

    //Only get 20 dogs once
    @GET("/v1/images/search?has_breeds=1&api_key=${Constants.API_KEY}")
    suspend fun getDogs(
        @Query("limit") limit: Int = 20,  // Default to 20 items per page
        @Query("page") page: Int = 0     // Add page parameter for pagination
    ): List<DogDto>

    @GET("/v1/images/{dogId}")
    suspend fun getDogById(@Path("dogId") dogId: String): DogDetailDto

}