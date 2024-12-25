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
* API information
* */
interface ReportCenterApi {

    // Get stock financial data list
    @GET("report/center/getList?")
    suspend fun getList(
        // Don't know what are these variables, but they are important and never change,
        // so keep them with default values
        @Query("is_center")is_center: Int = 1,
        @Query("chwm")chwm: String = "32010_0001",
        @Query("wm")wm: String = "b122",

        // Default important variables
        @Query("page") page: Int = 1, // Add page parameter for pagination
        @Query("page_size") page_size: Int = 20, // How many items in one page, default to 20

        // Individual important variables
        @Query("rating_change") rating_change: Int = 0, // 评级变动，默认为全部
        @Query("hy_code") hy_code: String = "", // 行业代码，默认为无
        @Query("qs_code") qs_code: String = "" // 券商代码，默认为无
    ): StockDto

    // Get hy rank data list
    @GET("report/center/getHyRank?")
    suspend fun getHyRank(
        // Don't know what are these variables, but they are important and never change,
        // so keep them with default values
        @Query("chwm")chwm: String = "32010_0001",
        @Query("wm")wm: String = "b122",

        // Default important variables
        @Query("page") page: Int = 1, // Add page parameter for pagination
        @Query("page_size") page_size: Int = 20, // How many items in one page, default to 20

        // Individual important variables
        @Query("type") type: Int = 1, // 申万几级，默认一级
        @Query("sort_type") sort_type: Int = 0, // 排序，默认高到低
        @Query("date_type") date_type: Int = 1, // 近多久的数据，默认近一周
        @Query("sort_col") sort_col: String = "num" // 按照什么排序，默认按照数量
    ): HyDto

    // Get qs rank data list
    @GET("report/center/getQsRank?")
    suspend fun getQsRank(
        // Don't know what are these variables, but they are important and never change,
        // so keep them with default values
        @Query("chwm")chwm: String = "32010_0001",
        @Query("wm")wm: String = "b122",

        // Default important variables
        @Query("page") page: Int = 1, // Add page parameter for pagination
        @Query("page_size") page_size: Int = 20, // How many items in one page, default to 20

        // Individual important variables
        @Query("sort_type") sort_type: Int = 0, // 排序，默认高到低
        @Query("date_type") date_type: Int = 1, // 近多久的数据，默认近一周
        @Query("sort_col") sort_col: String = "num", // 按照什么排序，默认按照数量
        @Query("is_top") is_top: Int = 0 // 是否为头部券商，默认为否
    ): QsDto

    // Get qs symbol rank data list
    @GET("report/center/getQsSymbolRank?")
    suspend fun getQsSymbolRank(
        // Don't know what are these variables, but they are important and never change,
        // so keep them with default values
        @Query("chwm")chwm: String = "32010_0001",
        @Query("wm")wm: String = "b122",

        // Default important variables
        @Query("page") page: Int = 1, // Add page parameter for pagination
        @Query("page_size") page_size: Int = 20, // How many items in one page, default to 20

        // Individual important variables
        @Query("sort_type") sort_type: Int = 0, // 排序，默认高到低
        @Query("date_type") date_type: Int = 1, // 近多久的数据，默认近一周
        @Query("sort_col") sort_col: String = "percent", // 按照什么排序，默认按照涨幅
        @Query("qs_code") qs_code: String // 券商代码，必须传参
    ): QsSymbolDto

    // Get stock detail information
    @GET("stock/api/openapi.php/ReportService.getShow?")
    suspend fun getShowById(
        @Query("rptid") rptid: String // 股票ID，必须传参
    ): StockDetailDto
}

