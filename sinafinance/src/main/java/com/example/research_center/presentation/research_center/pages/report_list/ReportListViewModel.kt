package com.example.research_center.presentation.research_center.pages.report_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.research_center.common.Resource
import com.example.research_center.domain.use_case.get_report.GetReportUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/*
* View Model for report list, used to maintain the state
* */
@HiltViewModel
class ReportListViewModel @Inject constructor(
    private val getReportUseCase: GetReportUseCase
): ViewModel() {

    private val _state = mutableStateOf(ReportListState())
    val state: State<ReportListState> = _state

    // Record current rating sort, only assign value inside this view model
    var currentRatingChange = 0
        private set

    var currentPage = 1
        private set

    init {
        _state.value = _state.value.copy(
            isRefreshing = true,
            isLoading = false
        )

        getReport(
            page = currentPage,
            rating_change = currentRatingChange
        )
    }

    fun ratingChange(rating_change: Int) {
        currentRatingChange = rating_change
        currentPage = 1

        getReport(
            page = currentPage,
            rating_change = currentRatingChange
        )
    }

    fun refresh(){
        _state.value = _state.value.copy(
            isRefreshing = true,
            isLoading = false
        )
        currentPage = 1

        getReport(
            page = currentPage,
            rating_change = currentRatingChange
        )
    }

    fun loadMore() {

        if (_state.value.isLoading || _state.value.isEndReached) return

        if (_state.value.isLoading) return
        _state.value = _state.value.copy(
            isRefreshing = false,
            isLoading = true
        )
        currentPage += 1

        getReport(
            page = currentPage,
            rating_change = currentRatingChange
        )
    }

    private fun getReport(page: Int, rating_change: Int) {
        getReportUseCase(
            page = page,
            rating_change = rating_change
        ).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    val newData = result.data ?: emptyList()
                    val oldList = _state.value.report

                    if (newData.size < 20) {
                        _state.value = _state.value.copy(
                            isEndReached = true
                        )
                    }

                    // If load more data
                    if (_state.value.isLoading) {
                        val appendedList = oldList + newData
                        _state.value = _state.value.copy(
                            isRefreshing = false,
                            isLoading = false,
                            report = appendedList,
                            isEndReached = _state.value.isEndReached || (newData.size < 20)
                        )
                    // If load first time/refresh/change rating
                    } else {
                        _state.value = _state.value.copy(
                            isRefreshing = false,
                            isLoading = false,
                            report = newData,
                            isEndReached = (newData.size < 20)
                        )
                    }
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isRefreshing = false,
                        error = result.message ?: "Unknown Error"
                    )
                }
                is Resource.Loading -> {
                    // TODO: Nothing todo here right now
                }
            }
        }.launchIn(viewModelScope)
    }
}