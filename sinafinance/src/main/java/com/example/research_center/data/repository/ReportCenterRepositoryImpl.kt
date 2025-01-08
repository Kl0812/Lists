package com.example.research_center.data.repository

import com.example.research_center.data.remote.ReportCenterApi
import com.example.research_center.data.remote.ReportDetailApi
import com.example.research_center.data.remote.dto.HyDto
import com.example.research_center.data.remote.dto.QsDto
import com.example.research_center.data.remote.dto.QsSymbolDto
import com.example.research_center.data.remote.dto.ReportDetailDto
import com.example.research_center.data.remote.dto.ReportDto
import com.example.research_center.domain.repository.ReportCenterRepository
import javax.inject.Inject

/*
* The implement of report center repository,
* can easily implement test use case
* */
class ReportCenterRepositoryImpl @Inject constructor(
    private val listApi: ReportCenterApi,
    private val detailApi: ReportDetailApi
) : ReportCenterRepository {

    override suspend fun getList(
        page: Int,
        rating_change: Int,
        hy_code: String,
        qs_code: String
    ): ReportDto {
        return listApi.getList(
            page = page,
            rating_change = rating_change,
            hy_code = hy_code,
            qs_code = qs_code
        )
    }

    override suspend fun getHyRank(
        page: Int,
        type: Int,
        sort_type: Int,
        date_type: Int,
        sort_col: String
    ): HyDto {
        return listApi.getHyRank(
            page = page,
            type = type,
            sort_type = sort_type,
            date_type = date_type,
            sort_col = sort_col
        )
    }

    override suspend fun getQsRank(
        page: Int,
        sort_type: Int,
        date_type: Int,
        sort_col: String,
        is_top: Int
    ): QsDto {
        return listApi.getQsRank(
            page = page,
            sort_type = sort_type,
            date_type = date_type,
            sort_col = sort_col,
            is_top = is_top
        )
    }

    override suspend fun getQsSymbolRank(
        page: Int,
        sort_type: Int,
        date_type: Int,
        sort_col: String,
        qs_code: String
    ): QsSymbolDto {
        return listApi.getQsSymbolRank(
            page = page,
            sort_type = sort_type,
            date_type = date_type,
            sort_col = sort_col,
            qs_code = qs_code
        )
    }

    override suspend fun getShowById(
        rptid: String
    ): ReportDetailDto {
        return detailApi.getShowById(
            rptid = rptid
        )
    }
}