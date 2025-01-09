package com.example.research_center.data.remote

import com.example.research_center.common.Resource
import com.example.research_center.domain.model.Stock
import kotlinx.coroutines.flow.Flow

interface StockService {

    suspend fun initSession(
        stockCode: String
    ): Resource<Unit>

    fun observeStock(): Flow<Stock>

    suspend fun closeSession()

    suspend fun getStock(): Stock

    companion object {
        const val BASE_URL = "wss://a.sinajs.cn"
    }

    sealed class Endpoints(val url: String) {
        object StockSocket: Endpoints("$BASE_URL/wskt?format=text")
    }

}