package com.example.research_center.domain.use_case.get_stock

import com.example.research_center.common.Resource
import com.example.research_center.data.remote.dto.toStock
import com.example.research_center.domain.model.Stock
import com.example.research_center.domain.repository.ReportCenterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

/*
* Set page loading state and set stock list information
* */
class GetStockUseCase @Inject constructor(
    private val repository: ReportCenterRepository
) {
    operator fun invoke(
        page: Int = 1,
        rating_change: Int = 0,
        hy_code: String = "",
        qs_code: String = ""
    ): Flow<Resource<List<Stock>>> = flow {
        try {
            emit(Resource.Loading())
            val stock = repository.getList(
                page = page,
                rating_change = rating_change,
                hy_code = hy_code,
                qs_code = qs_code
            ).toStock()
            emit(Resource.Success(stock))
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch(e: IOException) {
            emit(Resource.Error("Please check the internet"))
        }
    }
}