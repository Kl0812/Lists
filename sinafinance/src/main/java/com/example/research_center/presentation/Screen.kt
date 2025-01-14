package com.example.research_center.presentation

/*
* Screen navigation routes
* */
sealed class Screen(val route: String) {
    object ResearchCenterScreen: Screen("research_center")
    object ReportDetailScreen: Screen("report_detail")
    object QsSymbolListScreen: Screen("qs_symbol_list")
    object StockScreen: Screen("stock")
    object HyReportScreen: Screen("hy_report")
}