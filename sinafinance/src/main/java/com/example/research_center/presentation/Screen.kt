package com.example.research_center.presentation

/*
* Set screen navigation route
* */
sealed class Screen(val route: String) {
    object ResearchCenterScreen: Screen("stock_ranking")
    object StockDetailScreen: Screen("stock_detail_screen")
}