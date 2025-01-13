package com.example.research_center.presentation.hy_report

import com.example.research_center.domain.model.Report

data class HyReportState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isEndReached: Boolean = false,
    val reportList: List<Report> = emptyList(), // 这里用 Stock 或者别的model
    val error: String = ""
)
