package com.example.research_center.domain.repository

import com.example.research_center.data.remote.dto.StockDto

interface StockRepository {

    suspend fun getStocks(page: Int): StockDto

}