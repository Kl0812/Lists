package com.example.research_center.data.remote.dto

import com.example.research_center.domain.model.Stock

data class StockDto(
    val code: String,
    val `data`: List<Data>,
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

fun StockDto.toStock(): List<Stock> {
    return data.map { item ->
        Stock (
            adddate = item.adddate,
            orgname = item.orgname,
            report_id = item.report_id,
            title = item.title
        )
    }
}