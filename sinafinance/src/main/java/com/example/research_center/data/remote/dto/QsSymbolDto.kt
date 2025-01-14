package com.example.research_center.data.remote.dto

import com.example.research_center.domain.model.QsSymbol

// Class which contains json data transfer object
data class QsSymbolDto(
    val code: String,
    val `data`: List<Data>?,
    val err: String,
    val msg: String
) {
    data class Data(
        val code: String,
        val ctime: String,
        val date: String,
        val date_type: String,
        val dtime: String,
        val name: String,
        val percent: String,
        val qs_code: String
    )
}

// Function which convert DTO to customised model
fun QsSymbolDto.toQsSymbol(): List<QsSymbol> {
    return data?.map { item ->
        QsSymbol(
            code = item.code,
            name = item.name,
            date = item.date,
            percent = item.percent.toFloat()
        )
    } ?: emptyList()
}