package com.example.research_center.data.remote.dto

import com.example.research_center.domain.model.Stock
import kotlinx.serialization.Serializable

@Serializable
data class StockDto(
    val information: String
)

fun StockDto.toStock(): Stock {
    // Split the string by ","
    val data = information.split(",")

    // Get stock name. The code is passed by the route
    val firstField = data.getOrElse(0) { "" }
    val stockName = firstField.substringAfter("=", firstField)

    return Stock(
        stockName = stockName,
        top = data.getOrNull(1)?.toFloatOrNull() ?: 0f,
        ycp = data.getOrNull(2)?.toFloatOrNull() ?: 0f,
        rtp = data.getOrNull(3)?.toFloatOrNull() ?: 0f,
        htp = data.getOrNull(4)?.toFloatOrNull() ?: 0f,
        ltp = data.getOrNull(5)?.toFloatOrNull() ?: 0f,
        bp = data.getOrNull(6)?.toFloatOrNull() ?: 0f,
        ap = data.getOrNull(7)?.toFloatOrNull() ?: 0f,
        tq = data.getOrNull(8)?.toFloatOrNull() ?: 0f,
        ta = data.getOrNull(9)?.toFloatOrNull() ?: 0f
    )
}
