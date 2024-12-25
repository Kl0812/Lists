package com.example.research_center.data.repository

import com.example.research_center.data.remote.ReportCenterApi
import com.example.research_center.data.remote.dto.HyDto
import com.example.research_center.data.remote.dto.QsDto
import com.example.research_center.data.remote.dto.QsSymbolDto
import com.example.research_center.data.remote.dto.StockDetailDto
import com.example.research_center.data.remote.dto.StockDto
import com.example.research_center.domain.repository.ReportCenterRepository
import javax.inject.Inject

/*
* The implement of report center repository,
* can easily implement test use case
* */
class ReportCenterRepositoryImpl @Inject constructor(
    private val api: ReportCenterApi
) : ReportCenterRepository {

    override suspend fun getList(
        page: Int,
        rating_change: Int,
        hy_code: String,
        qs_code: String
    ): StockDto {
        return api.getList()
    }

    override suspend fun getHyRank(
        page: Int,
        type: Int,
        sort_type: Int,
        date_type: Int,
        sort_col: String
    ): HyDto {
        return api.getHyRank()
    }

    override suspend fun getQsRank(
        page: Int,
        sort_type: Int,
        date_type: Int,
        sort_col: String,
        is_top: Int
    ): QsDto {
        return  api.getQsRank()
    }

    override suspend fun getQsSymbolRank(
        page: Int,
        sort_type: Int,
        date_type: Int,
        sort_col: String,
        qs_code: String
    ): QsSymbolDto {
        return api.getQsSymbolRank(qs_code = qs_code)
    }

    override suspend fun getShowById(
        showId: String
    ): StockDetailDto {
        return api.getShowById(showId = showId)
    }
}