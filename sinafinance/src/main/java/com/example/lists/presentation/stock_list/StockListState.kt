package com.example.lists.presentation.stock_list

import com.example.lists.domain.model.Stock

data class StockListState (
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isLastPage: Boolean = false,
    val stocks: List<Stock> = emptyList(),
    val error: String = ""
)