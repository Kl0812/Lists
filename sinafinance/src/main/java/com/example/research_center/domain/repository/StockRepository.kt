package com.example.research_center.domain.repository

import com.example.research_center.common.Resource
import com.example.research_center.domain.model.Stock
import kotlinx.coroutines.flow.Flow

/*
* Interface for api functionality
* */
interface StockRepository {

    suspend fun initSession(stockCode: String): Resource<Unit>

    fun observeStock(): Flow<Stock>

    suspend fun closeSession()

    suspend fun getStock(): Stock
}