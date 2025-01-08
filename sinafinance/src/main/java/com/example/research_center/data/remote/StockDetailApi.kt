package com.example.research_center.data.remote

import com.example.research_center.data.remote.dto.HyDto
import com.example.research_center.data.remote.dto.QsDto
import com.example.research_center.data.remote.dto.QsSymbolDto
import com.example.research_center.data.remote.dto.ReportDetailDto
import com.example.research_center.data.remote.dto.ReportDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/*
* Report detail API information, use DETAIL_BASE_UR
* */
interface ReportDetailApi {

    // Get report detail information
    @GET("report/api/openapi.php/ReportService.getShow?")
    suspend fun getShowById(
        @Query("fromsrc") fromsrc: String = "app", // 不知道是什么参数，但影响" reportinfo"排版的换行
        @Query("rptid") rptid: String // 股票ID，必须传参
    ): ReportDetailDto
}

