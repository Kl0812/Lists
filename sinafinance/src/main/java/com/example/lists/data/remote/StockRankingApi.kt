package com.example.lists.data.remote

import com.example.lists.data.remote.dto.StockDto
import retrofit2.http.GET
import retrofit2.http.Query

/*
* API information
* */
interface StockRankingApi {

    //Only get 20 financial news
    @GET("stocknews/api/news/get?")
    suspend fun getStocks(
        @Query("fr") fr: String = "financeapp", // Resource from finance app
        @Query("page") page: Int = 1, // Add page parameter for pagination
        @Query("num") num: Int = 20, // Default to 20 items per page
        @Query("symbol") symbol: String = "sz000002", // Default symbol
        @Query("market") market: String = "cn" // CN market

    ): StockDto

}

