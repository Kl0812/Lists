package com.example.research_center.presentation.research_center.pages.qs_list

import com.example.research_center.domain.model.Qs

data class QsListState (
    val isRefreshing: Boolean = false,
    val isLoading: Boolean = false,
    val isEndReached: Boolean = false,
    val qs: List<Qs> = emptyList(),
    val error: String = ""
)