package com.example.research_center.data.repository

import com.example.research_center.common.Resource
import com.example.research_center.data.remote.StockService
import com.example.research_center.domain.model.Stock
import com.example.research_center.domain.repository.StockRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StockRepositoryImpl @Inject constructor(
    private val stockService: StockService
) : StockRepository {

    override suspend fun initSession(stockCode: String): Resource<Unit> {
        return stockService.initSession(stockCode)
    }

    override fun observeStock(): Flow<Stock> {
        return stockService.observeStock()
    }

    override suspend fun closeSession() {
        stockService.closeSession()
    }

    override suspend fun getStock(): Stock {
        return stockService.getStock()
    }
}