package com.example.research_center.data.remote.dto

import com.example.research_center.domain.model.Report

// Class which contains json data transfer object
data class ReportDto(
    val code: String,
    val `data`: List<Data>?,
    val err: String,
    val msg: String
) {
    data class Data(
        val adddate: String,
        val author: String,
        val goal_price: String,
        val orgname: String,
        val orgnamelong: String,
        val rating: String,
        val report_id: String,
        val symbol: String,
        val title: String
    )
}

// Function which convert DTO to customised model
fun ReportDto.toReport(): List<Report> {
    return data?.map { item ->
        Report (
            adddate = item.adddate,
            orgname = item.orgname,
            report_id = item.report_id,
            title = item.title
        )
    } ?: emptyList()
}