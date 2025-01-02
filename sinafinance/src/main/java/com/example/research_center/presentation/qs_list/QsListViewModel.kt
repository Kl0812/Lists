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

    var currentSortCol = "num"
        private set

    var currentSortType = 0
        private set

    var hasManualSort = false
        private set

    init {
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

        getQs(
            page = currentPage,
            date_type = currentDateType,
            is_top = currentIsTop,
            sort_type = currentSortType,
            sort_col = currentSortCol
        )
    }

    fun setSort(col: String, typeOrNone: Int) {
        Log.d("QsListViewModel", "request => typeOrNone=$typeOrNone ")
        hasManualSort = true
        if (typeOrNone == -1) {
            hasManualSort = false
            currentSortCol = "num"
            currentSortType = 0
        } else {
            currentSortCol = col
            currentSortType = typeOrNone
        }

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
        Log.d("QsListViewModel", "request => date_type=$date_type ")
        Log.d("QsListViewModel", "request => is_top=$is_top")
        Log.d("QsListViewModel", "request => sort_type=$sort_type")
        Log.d("QsListViewModel", "request => sort_col=$sort_col")
        getQsUseCase(
            page = page,
            date_type = date_type,
            is_top = is_top,
            sort_type = sort_type,
            sort_col = sort_col
        ).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _state.value = QsListState(
                        qs = result.data ?: emptyList()
                    )
                }
                is Resource.Error -> {
                    _state.value = QsListState(
                        error = result.message ?: "Unknown Error"
                    )
                }
                is Resource.Loading -> {
                    _state.value = QsListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}