package com.example.lists.domain.repository

import com.example.lists.data.remote.dto.StockDto

interface StockRepository {

    suspend fun getStocks(page: Int): StockDto

}