package com.example.research_center.presentation.research_center.pages.qs_list

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.research_center.common.Resource
import com.example.research_center.domain.use_case.get_qs.GetQsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/*
* View Model for qs list, used to maintain the state
* */
@HiltViewModel
class QsListViewModel @Inject constructor(
    private val getQsUseCase: GetQsUseCase
): ViewModel() {

    private val _state = mutableStateOf(QsListState())
    val state: State<QsListState> = _state

    // Get data by date
    var currentDateType = 1
        private set

    var currentPage = 1
        private set

    var currentIsTop = 0
        private set

    private val _currentSortCol = mutableStateOf("")
    val currentSortCol: State<String> = _currentSortCol

    private val _currentSortType = mutableStateOf(-1)
    val currentSortType: State<Int> = _currentSortType

    init {
        _state.value = _state.value.copy(
            isRefreshing = true,
            isLoading = false
        )

        getQs(
            page = currentPage,
            date_type = currentDateType,
            is_top = currentIsTop,
            sort_type = currentSortType.value,
            sort_col = currentSortCol.value
        )
    }

    fun dateType(date_type: Int) {
        currentDateType = date_type
        currentPage = 1

        getQs(
            page = currentPage,
            date_type = currentDateType,
            is_top = currentIsTop,
            sort_type = currentSortType.value,
            sort_col = currentSortCol.value
        )
    }

    fun isTop(checkTop: Boolean) {
        currentIsTop = if(checkTop) 1 else 0
        currentPage = 1

        getQs(
            page = currentPage,
            date_type = currentDateType,
            is_top = currentIsTop,
            sort_type = currentSortType.value,
            sort_col = currentSortCol.value
        )
    }

    fun setSort(col: String, typeOrNone: Int) {
        currentPage = 1

        _currentSortCol.value = if (typeOrNone == -1) "" else col
        _currentSortType.value = typeOrNone

        getQs(
            page = currentPage,
            date_type = currentDateType,
            is_top = currentIsTop,
            sort_type = currentSortType.value,
            sort_col = currentSortCol.value
        )
    }

    fun refresh(){
        _state.value = _state.value.copy(
            isRefreshing = true,
            isLoading = false,
            error = ""
        )
        currentPage = 1

        getQs(
            page = currentPage,
            date_type = currentDateType,
            is_top = currentIsTop,
            sort_type = currentSortType.value,
            sort_col = currentSortCol.value
        )
    }

    fun loadMore() {

        if (_state.value.isLoading || _state.value.isEndReached) return

        _state.value = _state.value.copy(
            isRefreshing = false,
            isLoading = true
        )
        currentPage += 1

        getQs(
            page = currentPage,
            date_type = currentDateType,
            is_top = currentIsTop,
            sort_type = currentSortType.value,
            sort_col = currentSortCol.value
        )
    }

    private fun getQs(
        page: Int,
        date_type: Int,
        is_top: Int,
        sort_type: Int,
        sort_col: String
    ) {
        getQsUseCase(
            page = page,
            date_type = date_type,
            is_top = is_top,
            sort_type = sort_type,
            sort_col = sort_col
        ).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    val newData = result.data ?: emptyList()
                    val oldList = _state.value.qs

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
                            qs = appendedList,
                            isEndReached = _state.value.isEndReached || (newData.size < 20)
                        )
                    // If load first time/refresh/change rating
                    } else {
                        _state.value = _state.value.copy(
                            isRefreshing = false,
                            isLoading = false,
                            qs = newData,
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