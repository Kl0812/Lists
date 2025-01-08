package com.example.research_center.domain.use_case.get_qsSymbol

import com.example.research_center.common.Resource
import com.example.research_center.data.remote.dto.toQsSymbol
import com.example.research_center.domain.model.QsSymbol
import com.example.research_center.domain.repository.ReportCenterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

/*
* Set page loading state and set qs symbol ranking list information
* */
class GetQsSymbolUseCase @Inject constructor(
    private val repository: ReportCenterRepository
) {
    operator fun invoke(
        qs_code: String,
        page: Int = 1,
        sort_type: Int = 0,
        date_type: Int = 1,
        sort_col: String = "percent"
    ): Flow<Resource<List<QsSymbol>>> = flow {
        try {
            emit(Resource.Loading())
            val qsSymbol = repository.getQsSymbolRank(
                page = page,
                sort_type = sort_type,
                date_type = date_type,
                sort_col = sort_col,
                qs_code = qs_code,
            ).toQsSymbol()
            emit(Resource.Success(qsSymbol))
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch(e: IOException) {
            emit(Resource.Error("Please check the internet"))
        }
    }
}