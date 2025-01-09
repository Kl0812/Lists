package com.example.research_center.data.remote

import com.example.research_center.data.remote.dto.ReportDetailDto
import retrofit2.http.GET
import retrofit2.http.Query

/*
* Report detail API information, use DETAIL_BASE_UR
* */
interface ReportDetailApi {

    // Get report detail information
    @GET("stock/api/openapi.php/ReportService.getShow?")
    suspend fun getShowById(
        @Query("fromsrc") fromsrc: String = "app", // 不知道是什么参数，但影响"reportinfo"排版的换行
        @Query("rptid") rptid: String // 研报ID，必须传参
    ): ReportDetailDto
}

