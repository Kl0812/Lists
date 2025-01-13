package com.example.research_center.presentation.hy_report

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.research_center.common.Constants.HY_CODE
import com.example.research_center.common.Constants.HY_NAME
import com.example.research_center.common.Resource
import com.example.research_center.domain.use_case.get_qsSymbol.GetQsSymbolUseCase
import com.example.research_center.domain.use_case.get_report.GetReportUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/*
* View Model for qs symbol list, used to maintain the state
* Also is contains GetReportUseCase for ReportListSection
* */
@HiltViewModel
class HyReportViewModel @Inject constructor(
    private val getReportUseCase: GetReportUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = mutableStateOf(HyReportState())
    val state: State<HyReportState> = _state

    var currentRatingChange = 0
        private set

    var currentPage = 1
        private set

    // 通过路径获取行业code
    private val hy_code = savedStateHandle.get<String>(HY_CODE)
    // If need to display hyCode in CustomTopBar
    // val hyCode: String? get() = hy_code

    // 通过路径获取行业name
    private val hy_name = savedStateHandle.get<String>(HY_NAME)
    val hyName: String? get() = hy_name

    init {
        _state.value = _state.value.copy(
            isRefreshing = true,
            isLoading = false
        )

        if (hy_code != null) {
            getReport(
                page = currentPage,
                rating_change = currentRatingChange,
                hy_code = hy_code
            )
        }
    }

    fun ratingChange(rating_change: Int) {
        currentRatingChange = rating_change
        currentPage = 1

        if (hy_code != null) {
            getReport(
                page = currentPage,
                rating_change = currentRatingChange,
                hy_code = hy_code
            )
        }
    }

    fun refresh(){
        _state.value = _state.value.copy(
            isRefreshing = true,
            isLoading = false
        )
        currentPage = 1

        if (hy_code != null) {
            getReport(
                page = currentPage,
                rating_change = currentRatingChange,
                hy_code = hy_code
            )
        }
    }

    fun loadMore() {

        if (_state.value.isLoading || _state.value.isEndReached) return

        if (_state.value.isLoading) return
        _state.value = _state.value.copy(
            isRefreshing = false,
            isLoading = true
        )
        currentPage += 1

        if (hy_code != null) {
            getReport(
                page = currentPage,
                rating_change = currentRatingChange,
                hy_code = hy_code
            )
        }
    }

    private fun getReport(
        page: Int,
        rating_change: Int,
        hy_code: String
    ) {
        getReportUseCase(
            page = page,
            rating_change = rating_change,
            hy_code = hy_code
        ).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    val newData = result.data ?: emptyList()
                    val oldList = _state.value.reportList

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
                            reportList = appendedList,
                            isEndReached = _state.value.isEndReached || (newData.size < 20)
                        )
                        // If load first time/refresh/change rating
                    } else {
                        _state.value = _state.value.copy(
                            isRefreshing = false,
                            isLoading = false,
                            reportList = newData,
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