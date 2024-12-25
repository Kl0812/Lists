package com.example.research_center.domain.use_case.get_stockDetail

import com.example.research_center.common.Resource
import com.example.research_center.data.remote.dto.toStockDetail
import com.example.research_center.domain.model.Stock
import com.example.research_center.domain.model.StockDetail
import com.example.research_center.domain.repository.ReportCenterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

/*
* Set page loading state and set stock detail information
* */
class GetStockDetailUseCase @Inject constructor(
    private val repository: ReportCenterRepository
) {
    operator fun invoke(
        rptid: String
    ): Flow<Resource<StockDetail>> = flow {
        try {
            emit(Resource.Loading())
            val stockDetail = repository.getShowById(
                rptid = rptid
            ).toStockDetail()
            emit(Resource.Success(stockDetail))
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch(e: IOException) {
            emit(Resource.Error("Please check the internet"))
        }
    }
}