package com.example.research_center.presentation.stock_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.research_center.common.Resource
import com.example.research_center.domain.use_case.get_stock.GetStockUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/*
* View Model for stock list, used to maintain the state
* */
@HiltViewModel
class StockListViewModel @Inject constructor(
    private val getStockUseCase: GetStockUseCase
): ViewModel() {

    private val _state = mutableStateOf(StockListState())
    val state: State<StockListState> = _state

    // Record current rating sort, only assign value inside this view model
    var currentRatingChange = 0
        private set

    init {
        getStock(rating_change = currentRatingChange)
    }

    fun ratingChangeStocks(rating_change: Int) {
        currentRatingChange = rating_change
        getStock(currentRatingChange)
    }

    fun refresh(){
        _state.value = _state.value.copy(isRefreshing = true)
        getStock(currentRatingChange)
    }

    private fun getStock(rating_change: Int) {
        getStockUseCase(
            rating_change = rating_change
        ).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _state.value = StockListState(
                        isRefreshing = false,
                        stock = result.data ?: emptyList()
                    )
                }
                is Resource.Error -> {
                    _state.value = StockListState(
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