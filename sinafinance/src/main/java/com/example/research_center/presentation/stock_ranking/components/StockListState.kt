package com.example.research_center.presentation.stock_ranking.components

data class StockListState (
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isLastPage: Boolean = false,
    val stocks: List<Stock> = emptyList(),
    val error: String = ""
)