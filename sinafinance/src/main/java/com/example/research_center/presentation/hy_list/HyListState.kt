package com.example.research_center.presentation.hy_list

import com.example.research_center.domain.model.Hy

data class HyListState (
    val isLoading: Boolean = false,
    val hy: List<Hy> = emptyList(),
    val error: String = ""
)