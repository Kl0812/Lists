package com.example.research_center.presentation.report_detail

import com.example.research_center.domain.model.ReportDetail

data class ReportDetailState (
    val isLoading: Boolean = false,
    val reportDetail: ReportDetail? = null,
    val error: String = ""
)