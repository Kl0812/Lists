package com.example.research_center.presentation.research_center.pages.hy_list

import com.example.research_center.domain.model.Hy

/*
* Class for store hy list state
* */
data class HyListState (
    val isRefreshing: Boolean = false,
    val isLoading: Boolean = false,
    val isEndReached: Boolean = false,
    val barChart: List<Hy> = emptyList(),
    val hy: List<Hy> = emptyList(),
    val error: String = ""
)