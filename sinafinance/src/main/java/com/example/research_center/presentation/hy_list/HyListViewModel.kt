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

    init {
        getHy()
    }

    private fun getHy() {
        getHyUseCase().onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _state.value = HyListState(
                        hy = result.data ?: emptyList()
                    )
                }
                is Resource.Error -> {
                    _state.value = HyListState(
                        error = result.message ?: "Unknown Error"
                    )
                }
                is Resource.Loading -> {
                    _state.value = HyListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}