package com.example.research_center.presentation.research_center.pages.report_list

import com.example.research_center.domain.model.Report

/*
* Class to store report list state
* */
data class ReportListState (
    val isRefreshing: Boolean = false,
    val isLoading: Boolean = false,
    val isEndReached: Boolean = false,
    val report: List<Report> = emptyList(),
    val error: String = ""
)