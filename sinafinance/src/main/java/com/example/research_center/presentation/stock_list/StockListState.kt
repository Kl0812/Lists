package com.example.research_center.presentation.stock_list

import com.example.research_center.domain.model.Stock

data class StockListState (
    val isRefreshing: Boolean = false,
    val isLoading: Boolean = false,
    val isEndReached: Boolean = false,
    val stock: List<Stock> = emptyList(),
    val error: String = ""
)