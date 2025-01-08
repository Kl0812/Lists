package com.example.research_center.domain.use_case.get_hy

import com.example.research_center.common.Resource
import com.example.research_center.data.remote.dto.toHy
import com.example.research_center.domain.model.Hy
import com.example.research_center.domain.repository.ReportCenterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

/*
* Set page loading state and set hy ranking list information
* */
class GetHyUseCase @Inject constructor(
    private val repository: ReportCenterRepository
) {
    operator fun invoke(
        page: Int = 1,
        type: Int = 1,
        sort_type: Int = 0,
        date_type: Int = 1,
        sort_col: String = "num"
    ): Flow<Resource<List<Hy>>> = flow {
        try {
            emit(Resource.Loading())
            val hy = repository.getHyRank(
                page = page,
                type = type,
                sort_type = sort_type,
                date_type = date_type,
                sort_col = sort_col
            ).toHy()
            emit(Resource.Success(hy))
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch(e: IOException) {
            emit(Resource.Error("Please check the internet"))
        }
    }
}