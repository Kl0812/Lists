package com.example.research_center.data.remote.dto

import com.example.research_center.domain.model.Stock
import kotlinx.serialization.Serializable

// Class which contains csv string from websocket connection
@Serializable
data class StockDto(
    val information: String
)

// Function which convert DTO to customised model
fun StockDto.toStock(): Stock {
    // Split the string by ","
    val data = information.split(",")

    // Get stock name. The code is passed by the route
    val firstField = data.getOrElse(0) { "" }
    val stockName = firstField.substringAfter("=", firstField)

    return Stock(
        stockName = stockName,
        top = data.getOrNull(1) ?: "",
        ycp = data.getOrNull(2) ?: "",
        rtp = data.getOrNull(3) ?: "",
        htp = data.getOrNull(4) ?: "",
        ltp = data.getOrNull(5) ?: "",
        bp = data.getOrNull(6) ?: "",
        ap = data.getOrNull(7) ?: "",
        tq = data.getOrNull(8) ?: "",
        ta = data.getOrNull(9) ?: ""
    )
}
