package com.example.research_center.data.remote.dto

import com.example.research_center.domain.model.Qs

data class QsDto(
    val code: String,
    val `data`: List<Data>,
    val err: String,
    val msg: String
) {
    data class Data(
        val code: String,
        val ctime: String,
        val date_type: String,
        val dtime: String,
        val name: String,
        val num: String,
        val percent: String
    )
}

fun QsDto.toQs(): List<Qs> {
    return data.map { item ->
        Qs(
            name = item.name,
            percent = item.percent.toFloat(),
            num = item.num.toInt(),
            code = item.code
        )
    }
}