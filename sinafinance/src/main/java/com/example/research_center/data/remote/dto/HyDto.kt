package com.example.research_center.data.remote.dto

import com.example.research_center.domain.model.Hy

data class HyDto(
    val code: String,
    val `data`: List<Data>?,
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
        val symbol: String,
        val symbol_name: String,
        val symbol_num: String,
        val type: String
    )
}

fun HyDto.toHy(): List<Hy> {
    return data?.map { item ->
        Hy(
            name = item.name,
            code = item.code,
            num = item.num.toInt(),
            symbol_name = item.symbol_name,
            symbol_num = item.symbol_num.toInt()
        )
    } ?: emptyList()
}