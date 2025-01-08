package com.example.research_center.presentation.report_list

import com.example.research_center.domain.model.Report

data class ReportListState (
    val isRefreshing: Boolean = false,
    val isLoading: Boolean = false,
    val isEndReached: Boolean = false,
    val report: List<Report> = emptyList(),
    val error: String = ""
)