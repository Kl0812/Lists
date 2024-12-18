package com.example.lists.presentation.stock_list

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lists.common.Resource
import com.example.lists.domain.use_case.get_stocks.GetStocksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/*
* View Model for stock list, used to pass values to list screen
* */
@HiltViewModel
class StockListViewModel @Inject constructor(
    private val getStocksUseCase: GetStocksUseCase

): ViewModel() {

    private val _state = mutableStateOf(StockListState())
    val state: State<StockListState> = _state

    private var currentPage = 1
    private var isLastPage = false

    // Initialization function
    init {
        refreshStocks()
    }

    // Handle actions for each refreshing situation,
    // include load data for the first time when open the app
    // The reason why internal fun is because refreshStocks need to be used
    // everytime when refreshing or load more data
    internal fun refreshStocks() {
        currentPage = 1
        isLastPage = false
        _state.value = _state.value.copy(
            isRefreshing = true,
            isLoading = false,
            error = ""
        )
        fetchStocks()
    }

    // Handle actions for each loading situation
    // The reason why internal fun is because loadMoreStocks need to be used
    // everytime when refreshing or load more data
    internal fun loadMoreStocks() {
        if (_state.value.isLoading || _state.value.isRefreshing || isLastPage) return
        _state.value = _state.value.copy(isLoading = true)
        fetchStocks()
    }

    // Main function to get stocks data
    private fun fetchStocks() {
        getStocksUseCase(currentPage).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    val newStocks = result.data ?: emptyList()
                    if (newStocks.isEmpty()) {
                        isLastPage = true
                    }

                    // According to onRefresh and onLoad, decide how to deal with the data list
                    val updatedStocks = if (_state.value.isRefreshing) {
                        newStocks
                    } else {
                        _state.value.stocks + newStocks
                    }

                    // Refreshing loading states
                    _state.value = _state.value.copy(
                        stocks = updatedStocks,
                        isLoading = false,
                        isRefreshing = false,
                        error = ""
                    )
                    currentPage++
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message ?: "An Unexpected Error Occur",
                        isLoading = false,
                        isRefreshing = false
                    )
                }
                is Resource.Loading -> {
                    // Do nothing. Because the loading state is
                    // set in refreshStocks and loadMoreStocks
                }
            }
        }.launchIn(viewModelScope)
    }

}