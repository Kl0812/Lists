package com.example.lists.data.remote.dto

import com.example.lists.domain.model.Stock

data class StockDto(
    val result: Result
) {
    data class Result(
        val `data`: List<Data>,
        val status: Status,
        val timestamp: String
    ) {
        data class Data(
            val create_date: String,
            val create_time: String,
            val docid: String,
            val label: String,
            val media_source: String,
            val short_title: String,
            val snv: String,
            val title: String,
            val url: String,
            val wapurl: String
        )

        data class Status(
            val code: Int,
            val msg: String
        )
    }
}

fun StockDto.toStock(): List<Stock> {
    return result.data.map { item ->
        Stock(
            create_date = item.create_date,
            create_time = item.create_time,
            title = item.title,
            media_source = item.media_source,
            wapUrl = item.wapurl
        )
    }
}