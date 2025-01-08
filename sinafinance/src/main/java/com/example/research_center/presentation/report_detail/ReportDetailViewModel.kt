package com.example.research_center.presentation.report_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.research_center.common.Constants
import com.example.research_center.common.Resource
import com.example.research_center.domain.use_case.get_reportDetail.GetReportDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/*
* View Model for report detail, used to maintain the state
* */
@HiltViewModel
class ReportDetailViewModel @Inject constructor(
    private val getReportDetailUseCase: GetReportDetailUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = mutableStateOf(ReportDetailState())
    val state: State<ReportDetailState> = _state

    init {
        savedStateHandle.get<String>(Constants.REPORT_ID)?.let{ rptid ->
            getReportDetail(rptid)
        }
    }

    private fun getReportDetail(rptid: String) {
        getReportDetailUseCase(rptid).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _state.value = ReportDetailState(
                        reportDetail = result.data
                    )
                }
                is Resource.Error -> {
                    _state.value = ReportDetailState(
                        error = result.message ?: "Unknown Error"
                    )
                }
                is Resource.Loading -> {
                    _state.value = ReportDetailState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}