package com.example.research_center.domain.repository

import com.example.research_center.data.remote.dto.HyDto
import com.example.research_center.data.remote.dto.QsDto
import com.example.research_center.data.remote.dto.QsSymbolDto
import com.example.research_center.data.remote.dto.ReportDetailDto
import com.example.research_center.data.remote.dto.ReportDto

/*
* Interface for api functionality, can also implement cache here
* */
interface ReportCenterRepository {

    suspend fun getList(
        page: Int,
        rating_change: Int,
        hy_code: String,
        qs_code: String
    ): ReportDto

    suspend fun getHyRank(
        page: Int,
        type: Int,
        sort_type: Int,
        date_type: Int,
        sort_col: String
    ): HyDto

    suspend fun getQsRank(
        page: Int,
        sort_type: Int,
        date_type: Int,
        sort_col: String,
        is_top: Int
    ): QsDto

    suspend fun getQsSymbolRank(
        page: Int,
        sort_type: Int,
        date_type: Int,
        sort_col: String,
        qs_code: String
    ): QsSymbolDto

    suspend fun getShowById(
        rptid: String
    ): ReportDetailDto

}