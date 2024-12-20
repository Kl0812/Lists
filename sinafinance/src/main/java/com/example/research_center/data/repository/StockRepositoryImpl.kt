package com.example.research_center.data.repository

import com.example.research_center.data.remote.StockRankingApi
import com.example.research_center.data.remote.dto.StockDto
import com.example.research_center.domain.repository.StockRepository
import javax.inject.Inject

/*
* The implement of stock repository
* */
class StockRepositoryImpl @Inject constructor(
    private val api: StockRankingApi
) : StockRepository {

    override suspend fun getStocks(page: Int): StockDto {
        return api.getStocks(page = page)
    }

}