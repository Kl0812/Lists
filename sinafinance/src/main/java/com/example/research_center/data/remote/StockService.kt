package com.example.research_center.data.remote

import com.example.research_center.common.Constants.STOCK_BASE_URL
import com.example.research_center.common.Resource
import com.example.research_center.domain.model.Stock
import kotlinx.coroutines.flow.Flow

/*
* Stock API websocket path, use STOCK_BASE_URL
* */
interface StockService {
    suspend fun initSession(
        stockCode: String
    ): Resource<Unit>

    fun observeStock(): Flow<Stock>

    suspend fun closeSession()

    suspend fun getStock(): Stock

    sealed class Endpoints(val url: String) {
        object StockSocket: Endpoints("$STOCK_BASE_URL/wskt?format=text")
    }

}