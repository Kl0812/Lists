package com.example.research_center.presentation.qs_symbol_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.research_center.common.Constants
import com.example.research_center.common.Resource
import com.example.research_center.domain.use_case.get_qsSymbol.GetQsSymbolUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/*
* View Model for qs symbol list, used to maintain the state
* */
@HiltViewModel
class QsSymbolListViewModel @Inject constructor(
    private val getQsSymbolUseCase: GetQsSymbolUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = mutableStateOf(QsSymbolListState())
    val state: State<QsSymbolListState> = _state

    init {
        savedStateHandle.get<String>(Constants.QS_CODE)?.let{ qs_code ->
            getQsSymbol(qs_code)
        }
    }

    private fun getQsSymbol(qs_code: String) {
        getQsSymbolUseCase(qs_code = qs_code).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _state.value = QsSymbolListState(
                        qsSymbol = result.data ?: emptyList()
                    )
                }
                is Resource.Error -> {
                    _state.value = QsSymbolListState(
                        error = result.message ?: "Unknown Error"
                    )
                }
                is Resource.Loading -> {
                    _state.value = QsSymbolListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}