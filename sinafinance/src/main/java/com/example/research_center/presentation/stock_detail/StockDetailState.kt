package com.example.research_center.presentation.stock_detail

import com.example.research_center.domain.model.StockDetail

data class StockDetailState (
    val isLoading: Boolean = false,
    val detail: StockDetail? = null,
    val error: String = ""
)