package com.example.research_center.presentation.stock

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.research_center.common.Constants.STOCK_CODE
import com.example.research_center.common.Resource
import com.example.research_center.domain.repository.StockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
* View model for stock
* Use websocket to establish a connection and retrieve data continually
* */
@HiltViewModel
class StockViewModel @Inject constructor(
    private val stockRepository: StockRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _toastEvent = MutableSharedFlow<String>()
    val toastEvent = _toastEvent.asSharedFlow()

    private val _state = mutableStateOf(StockState())
    val state: State<StockState> = _state

    // 通过路径获取股票code
    private val stock_code = savedStateHandle.get<String>(STOCK_CODE)
    val stockCode: String? get() = stock_code

    init {
        connectToStock()
    }

    private fun connectToStock() {
        val code = stockCode
        if (code.isNullOrBlank()) {
            viewModelScope.launch {
                _toastEvent.emit("No stock_code provided.")
            }
            return
        }

        viewModelScope.launch {
            val result = stockRepository.initSession(code)
            when (result) {
                is Resource.Success -> {
                    stockRepository.observeStock()
                        .onEach { stock ->
                            _state.value = _state.value.copy(
                                stock = stock,
                                isLoading = false
                            )
                        }
                        .launchIn(viewModelScope)
                }
                is Resource.Error -> {
                    _toastEvent.emit(result.message ?: "Unknown error")
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }
    }

    private fun disconnect() {
        viewModelScope.launch {
            stockRepository.closeSession()
        }
    }

    override fun onCleared() {
        super.onCleared()
        disconnect()
    }
}