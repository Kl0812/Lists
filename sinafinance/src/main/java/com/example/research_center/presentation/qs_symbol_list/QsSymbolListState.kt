package com.example.research_center.presentation.qs_symbol_list

import com.example.research_center.domain.model.QsSymbol
import com.example.research_center.domain.model.Stock

data class QsSymbolListState (
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isEndReached: Boolean = false,
    val qsSymbol: List<QsSymbol> = emptyList(),
    val reportList: List<Stock> = emptyList(),
    val error: String = ""
)