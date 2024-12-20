package com.example.research_center.domain.use_case.get_stocks

import com.example.research_center.common.Resource
import com.example.research_center.data.remote.dto.toStock
import com.example.research_center.domain.model.Stock
import com.example.research_center.domain.repository.StockRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/*
* Set page loading state and set dog list information
* */
class GetStocksUseCase @Inject constructor(
    private val repository: StockRepository
) {
    operator fun invoke(page: Int): Flow<Resource<List<Stock>>> = flow {
        try {
            val response = repository.getStocks(page = page)
            if (response.result.status.code == 0) {
                val stocks = response.toStock() // Convert StockDto to stock data list
                emit(Resource.Success(stocks))
            } else {
                emit(Resource.Error("Error: ${response.result.status.msg}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}