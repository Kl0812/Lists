package com.example.research_center.presentation.stock_ranking.components

import com.example.research_center.domain.model.Stock

data class StockListState (
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isLastPage: Boolean = false,
    val stocks: List<Stock> = emptyList(),
    val error: String = ""
)