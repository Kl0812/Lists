package com.example.research_center.presentation.qs_list

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

    init {
        getQs(
            page = currentPage,
            date_type = currentDateType,
            is_top = currentIsTop
        )
    }

    fun dateType(date_type: Int) {
        currentDateType = date_type
        currentPage = 1

        getQs(
            page = currentPage,
            date_type = currentDateType,
            is_top = currentIsTop
        )
    }

    fun isTop(checkTop: Boolean) {
        currentIsTop = if(checkTop) 1 else 0

        getQs(
            page = currentPage,
            date_type = currentDateType,
            is_top = currentIsTop
        )
    }

    private fun getQs(
        page: Int,
        date_type: Int,
        is_top: Int
    ) {
        getQsUseCase(
            page = page,
            date_type = date_type,
            is_top = is_top
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