package com.example.lists.presentation

/*
* Set screen navigation route
* */
sealed class Screen(val route: String) {
    object StockListScreen: Screen("stock_list_screen")
    object StockDetailScreen: Screen("stock_detail_screen")
}