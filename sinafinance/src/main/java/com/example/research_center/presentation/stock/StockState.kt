package com.example.research_center.presentation.stock

import com.example.research_center.domain.model.Stock

/*
* Class to store stock state
* */
data class StockState(
    val stock: Stock? = null,
    val isLoading: Boolean = false,
    val error: String = ""
)