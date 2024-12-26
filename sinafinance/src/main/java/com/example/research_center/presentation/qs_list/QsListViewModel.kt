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

    init {
        getQs()
    }

    private fun getQs() {
        getQsUseCase().onEach { result ->
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