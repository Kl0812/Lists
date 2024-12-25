package com.example.research_center.domain.use_case.get_qs

import com.example.research_center.common.Resource
import com.example.research_center.data.remote.dto.toQs
import com.example.research_center.data.remote.dto.toStock
import com.example.research_center.domain.model.Qs
import com.example.research_center.domain.model.Stock
import com.example.research_center.domain.repository.ReportCenterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

/*
* Set page loading state and set qs ranking list information
* */
class GetQsUseCase @Inject constructor(
    private val repository: ReportCenterRepository
) {
    operator fun invoke(
        page: Int = 1,
        sort_type: Int = 0,
        date_type: Int = 1,
        sort_col: String = "num",
        is_top: Int = 0
    ): Flow<Resource<List<Qs>>> = flow {
        try {
            emit(Resource.Loading())
            val qs = repository.getQsRank(
                page = page,
                sort_type = sort_type,
                date_type = date_type,
                sort_col = sort_col,
                is_top = is_top
            ).toQs()
            emit(Resource.Success(qs))
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch(e: IOException) {
            emit(Resource.Error("Please check the internet"))
        }
    }
}