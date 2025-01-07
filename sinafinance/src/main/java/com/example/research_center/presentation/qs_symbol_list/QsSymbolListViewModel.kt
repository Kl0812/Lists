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
    private val qs_code = savedStateHandle.get<String>("qs_code")
    val qsCode: String? get() = qs_code

    init {
        _state.value = _state.value.copy(
            isRefreshing = true,
            isLoading = false
        )

        if(qs_code != null) {
            getQsSymbol(qs_code)
        } else {
            _state.value = QsSymbolListState(error="qs_code is null")
        }
    }

    fun refresh(){
        _state.value = _state.value.copy(
            isRefreshing = true,
            isLoading = false
        )
        currentPage = 1

        // TODO
    }

    fun setSort(col: String, typeOrNone: Int) {
        currentPage = 1

        _currentSortCol.value = col
        _currentSortType.value = typeOrNone

        // TODO

    }

    fun dateType(date_type: Int) {
        currentDateType = date_type
        currentPage = 1

        if(qs_code != null) {
            getQsSymbol(qs_code)
        } else {
            _state.value = QsSymbolListState(error="qs_code is null")
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
                    // TODO: Nothing todo here right now
                }
            }
        }.launchIn(viewModelScope)
    }
}