package com.example.research_center.presentation.stock_detail

import com.example.research_center.domain.model.StockDetail

data class StockDetailState (
    val isLoading: Boolean = false,
    val stockDetail: StockDetail? = null,
    val error: String = ""
)