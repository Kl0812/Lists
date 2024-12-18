package com.example.lists.data.repository

import com.example.lists.data.remote.StockRankingApi
import com.example.lists.data.remote.dto.StockDto
import com.example.lists.domain.repository.StockRepository
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