package com.example.research_center.data.remote

import com.example.research_center.data.remote.dto.HyDto
import com.example.research_center.data.remote.dto.QsDto
import com.example.research_center.data.remote.dto.QsSymbolDto
import com.example.research_center.data.remote.dto.StockDetailDto
import com.example.research_center.data.remote.dto.StockDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/*
* Stock detail API information, use DETAIL_BASE_UR
* */
interface StockDetailApi {

    // Get stock detail information
    @GET("stock/api/openapi.php/ReportService.getShow?")
    suspend fun getShowById(
        @Query("rptid") rptid: String // 股票ID，必须传参
    ): StockDetailDto
}

