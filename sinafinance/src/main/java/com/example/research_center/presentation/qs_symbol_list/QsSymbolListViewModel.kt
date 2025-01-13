package com.example.research_center.presentation.qs_symbol_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.research_center.common.Constants.QS_CODE
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
class QsSymbolListViewModel @Inject constructor(
    private val getQsSymbolUseCase: GetQsSymbolUseCase,
    private val getReportUseCase: GetReportUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = mutableStateOf(QsSymbolListState())
    val state: State<QsSymbolListState> = _state

    var currentDateType = 1
        private set

    var currentPage = 1
        private set

    private val _currentSortCol = mutableStateOf("")
    val currentSortCol: State<String> = _currentSortCol

    private val _currentSortType = mutableStateOf(-1)
    val currentSortType: State<Int> = _currentSortType

    // 通过路径获取证券code
    // 由于证券code等于证券name
    // 所以路径名既可以用作qs_code，也可以用作qs name
    private val qs_code = savedStateHandle.get<String>(QS_CODE)
    val qsCode: String? get() = qs_code

    init {
        _state.value = _state.value.copy(
            isRefreshing = true,
            isLoading = false
        )

        if(qs_code != null) {
            getQsSymbol(
                qs_code = qs_code,
                date_type = currentDateType,
                sort_type = _currentSortType.value,
                sort_col = _currentSortCol.value
            )
        } else {
            _state.value = _state.value.copy(
                error="qs_code is null"
            )
        }

        if(qs_code != null) {
            getReport(
                qs_code = qs_code,
                page = currentPage
            )
        } else {
            _state.value = _state.value.copy(
                error="qs_code is null"
            )
        }
    }

    fun refresh(){
        _state.value = _state.value.copy(
            isRefreshing = true,
            isLoading = false,
            error = ""
        )
        currentPage = 1

        if(qs_code != null) {
            getQsSymbol(
                qs_code = qs_code,
                date_type = currentDateType,
                sort_type = _currentSortType.value,
                sort_col = _currentSortCol.value
            )
        } else {
            _state.value = _state.value.copy(
                error="qs_code is null"
            )
        }

        if(qs_code != null) {
            getReport(
                qs_code = qs_code,
                page = currentPage
            )
        } else {
            _state.value = _state.value.copy(
                error="qs_code is null"
            )
        }
    }

    fun setSort(col: String, typeOrNone: Int) {
        _currentSortCol.value = if (typeOrNone == -1) "" else col
        _currentSortType.value = typeOrNone

        if(qs_code != null) {
            getQsSymbol(
                qs_code = qs_code,
                date_type = currentDateType,
                sort_type = typeOrNone,
                sort_col = _currentSortCol.value
            )
        } else {
            _state.value = _state.value.copy(
                error="qs_code is null"
            )
        }
    }

    fun dateType(date_type: Int) {
        currentDateType = date_type

        if(qs_code != null) {
            getQsSymbol(
                qs_code = qs_code,
                date_type = currentDateType,
                sort_type = _currentSortType.value,
                sort_col = _currentSortCol.value
            )
        } else {
            _state.value = _state.value.copy(
                error="qs_code is null"
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

        if(qs_code != null) {
            getReport(
                qs_code = qs_code,
                page = currentPage
            )
        } else {
            _state.value = _state.value.copy(
                error="qs_code is null"
            )
        }
    }

    private fun getQsSymbol(
        qs_code: String,
        date_type: Int,
        sort_type: Int,
        sort_col: String
    ) {
        getQsSymbolUseCase(
            qs_code = qs_code,
            date_type = date_type,
            sort_type = sort_type,
            sort_col = sort_col
        ).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isRefreshing = false,
                        qsSymbol = result.data ?: emptyList()
                    )
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

    private fun getReport(
        page: Int,
        qs_code: String
    ) {
        getReportUseCase(
            page = page,
            qs_code = qs_code
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