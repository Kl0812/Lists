package com.example.research_center.data.remote.dto

import com.example.research_center.domain.model.ReportDetail

data class ReportDetailDto(
    val result: Result
) {
    data class Result(
        val `data`: Data,
        val status: Status
    ) {
        data class Data(
            val `data`: Data,
            val total: Int
        ) {
            data class Data(
                val adddate: String,
                val author: String,
                val id: String,
                val industry: String,
                val isvalid: String,
                val orgname: String,
                val report_id: String,
                val reportinfo: String,
                val rpt_name: String,
                val share_url: String,
                val symbol: String,
                val title: String
            )
        }

        data class Status(
            val code: Int
        )
    }
}

fun ReportDetailDto.toReportDetail(): ReportDetail {
    return ReportDetail (
            title = result.data.data.title,
            orgname = result.data.data.orgname,
            author = result.data.data.author,
            adddate = result.data.data.adddate,
            reportinfo = result.data.data.reportinfo
        )
}