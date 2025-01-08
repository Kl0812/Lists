package com.example.research_center.domain.use_case.get_reportDetail

import com.example.research_center.common.Resource
import com.example.research_center.data.remote.dto.toReportDetail
import com.example.research_center.domain.model.ReportDetail
import com.example.research_center.domain.repository.ReportCenterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

/*
* Set page loading state and set report detail information
* */
class GetReportDetailUseCase @Inject constructor(
    private val repository: ReportCenterRepository
) {
    operator fun invoke(
        rptid: String
    ): Flow<Resource<ReportDetail>> = flow {
        try {
            emit(Resource.Loading())
            val reportDetail = repository.getShowById(
                rptid = rptid
            ).toReportDetail()
            emit(Resource.Success(reportDetail))
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch(e: IOException) {
            emit(Resource.Error("Please check the internet"))
        }
    }
}