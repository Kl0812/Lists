package com.example.research_center.presentation.hy_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.research_center.common.Resource
import com.example.research_center.domain.use_case.get_hy.GetHyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/*
* View Model for hy list, used to maintain the state
* */
@HiltViewModel
class HyListViewModel @Inject constructor(
    private val getHyUseCase: GetHyUseCase
): ViewModel() {

    private val _state = mutableStateOf(HyListState())
    val state: State<HyListState> = _state

    var currentDateType = 1
        private set

    var currentSwType = 1
        private set

    var currentPage = 1
        private set

    private val _currentSortType = mutableStateOf(-1)
    val currentSortType: State<Int> = _currentSortType

    init {
        _state.value = _state.value.copy(
            isRefreshing = true,
            isLoading = false
        )

        getHy(
            page = currentPage,
            date_type = currentDateType,
            sort_type = currentSortType.value,
            type = currentSwType
        )
    }

    fun dateType(date_type: Int) {
        currentDateType = date_type
        currentPage = 1

        getHy(
            page = currentPage,
            date_type = currentDateType,
            sort_type = currentSortType.value,
            type = currentSwType
        )
    }

    fun swType(type: Int) {
        currentSwType = type
        currentPage = 1

        getHy(
            page = currentPage,
            date_type = currentDateType,
            sort_type = currentSortType.value,
            type = currentSwType
        )
    }

    fun setSort(col: String, typeOrNone: Int) {
        currentPage = 1

        _currentSortType.value = typeOrNone

        getHy(
            page = currentPage,
            date_type = currentDateType,
            sort_type = currentSortType.value,
            type = currentSwType
        )
    }

    fun refresh(){
        _state.value = _state.value.copy(
            isRefreshing = true,
            isLoading = false
        )
        currentPage = 1

        getHy(
            page = currentPage,
            date_type = currentDateType,
            sort_type = currentSortType.value,
            type = currentSwType
        )
    }

    fun loadMore() {
        if (_state.value.isLoading || _state.value.isEndReached) return

        _state.value = _state.value.copy(
            isRefreshing = false,
            isLoading = true
        )
        currentPage += 1

        getHy(
            page = currentPage,
            date_type = currentDateType,
            sort_type = currentSortType.value,
            type = currentSwType
        )
    }

    private fun getHy(
        page: Int,
        date_type: Int,
        sort_type: Int,
        type: Int
    ) {
        getHyUseCase(
            page = page,
            date_type = date_type,
            sort_type = sort_type,
            type = type
        ).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    val newData = result.data ?: emptyList()
                    val oldList = _state.value.hy

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
                            hy = appendedList,
                            isEndReached = _state.value.isEndReached || (newData.size < 20)
                        )
                        // If load first time/refresh/change rating
                    } else {
                        _state.value = _state.value.copy(
                            isRefreshing = false,
                            isLoading = false,
                            hy = newData,
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