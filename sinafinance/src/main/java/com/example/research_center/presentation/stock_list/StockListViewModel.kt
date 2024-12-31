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

    var currentPage = 1
        private set

    init {
        getStock(
            page = currentPage,
            rating_change = currentRatingChange
        )
    }

    fun ratingChangeStocks(rating_change: Int) {
        currentRatingChange = rating_change
        currentPage = 1

        getStock(
            page = currentPage,
            rating_change = currentRatingChange
        )
    }

    fun refresh(){
        _state.value = _state.value.copy(
            isRefreshing = true,
            isLoading = false
        )
        currentPage = 1

        getStock(
            page = currentPage,
            rating_change = currentRatingChange
        )
    }

    fun loadMore() {
        if (_state.value.isLoading) return
        _state.value = _state.value.copy(
            isRefreshing = false,
            isLoading = true
        )
        currentPage += 1

        getStock(
            page = currentPage,
            rating_change = currentRatingChange
        )
    }

    private fun getStock(page: Int, rating_change: Int) {
        getStockUseCase(
            page = page,
            rating_change = rating_change
        ).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    val newData = result.data ?: emptyList()
                    val oldList = _state.value.stock

                    // If load more data
                    if (_state.value.isLoading) {

                        val appendedList = oldList + newData

                        _state.value = StockListState(
                            isRefreshing = false,
                            isLoading = false,
                            stock = appendedList
                        )
                    // If load first time/refresh/change rating
                    } else {
                        _state.value = StockListState(
                            isRefreshing = false,
                            isLoading = false,
                            stock = newData
                        )
                    }
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