package com.example.research_center.presentation.hy_report

import com.example.research_center.domain.model.Report

/*
* Class for store hy report state
* */
data class HyReportState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isEndReached: Boolean = false,
    val reportList: List<Report> = emptyList(),
    val error: String = ""
)
