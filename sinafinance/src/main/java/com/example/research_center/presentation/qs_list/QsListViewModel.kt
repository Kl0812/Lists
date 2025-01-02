package com.example.research_center.presentation.qs_list

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

    var currentSortCol = ""
        private set

    var currentSortType = -1
        private set

    init {
        _state.value = _state.value.copy(
            isRefreshing = true,
            isLoading = false
        )

        getQs(
            page = currentPage,
            date_type = currentDateType,
            is_top = currentIsTop,
            sort_type = currentSortType,
            sort_col = currentSortCol
        )
    }

    fun dateType(date_type: Int) {
        currentDateType = date_type
        currentPage = 1

        getQs(
            page = currentPage,
            date_type = currentDateType,
            is_top = currentIsTop,
            sort_type = currentSortType,
            sort_col = currentSortCol
        )
    }

    fun isTop(checkTop: Boolean) {
        currentIsTop = if(checkTop) 1 else 0
        currentPage = 1

        getQs(
            page = currentPage,
            date_type = currentDateType,
            is_top = currentIsTop,
            sort_type = currentSortType,
            sort_col = currentSortCol
        )
    }

    fun setSort(col: String, typeOrNone: Int) {
        currentPage = 1

        currentSortCol = col
        currentSortType = typeOrNone

        getQs(
            page = currentPage,
            date_type = currentDateType,
            is_top = currentIsTop,
            sort_type = currentSortType,
            sort_col = currentSortCol
        )
    }

    fun refresh(){
        _state.value = _state.value.copy(
            isRefreshing = true,
            isLoading = false
        )
        currentPage = 1

        getQs(
            page = currentPage,
            date_type = currentDateType,
            is_top = currentIsTop,
            sort_type = currentSortType,
            sort_col = currentSortCol
        )
    }

    fun loadMore() {
        if (_state.value.isLoading) return
        _state.value = _state.value.copy(
            isRefreshing = false,
            isLoading = true
        )
        currentPage += 1

        getQs(
            page = currentPage,
            date_type = currentDateType,
            is_top = currentIsTop,
            sort_type = currentSortType,
            sort_col = currentSortCol
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

                    // If load more data
                    if (_state.value.isLoading) {

                        val appendedList = oldList + newData

                        _state.value = QsListState(
                            isRefreshing = false,
                            isLoading = false,
                            qs = appendedList
                        )
                        // If load first time/refresh/change rating
                    } else {
                        _state.value = QsListState(
                            isRefreshing = false,
                            isLoading = false,
                            qs = newData
                        )
                    }
                }
                is Resource.Error -> {
                    _state.value = QsListState(
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