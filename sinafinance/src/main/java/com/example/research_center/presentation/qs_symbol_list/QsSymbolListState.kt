package com.example.research_center.presentation.qs_symbol_list

import com.example.research_center.domain.model.QsSymbol

data class QsSymbolListState (
    val isLoading: Boolean = false,
    val qsSymbol: List<QsSymbol> = emptyList(),
    val error: String = ""
)