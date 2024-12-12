package com.example.lists.presentation.dog_list

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lists.common.Resource
import com.example.lists.domain.use_case.get_dogs.GetDogsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/*
* View Model for dog list, used to pass values to list screen
* */
@HiltViewModel
class DogListViewModel @Inject constructor(
    private val getDogsUseCase: GetDogsUseCase

): ViewModel() {

    private val _state = mutableStateOf(DogListState())
    val state : State<DogListState> = _state

    private var currentPage = 0
    private var isLastPage = false

    init {
        //Use refresh logic even open app for the first time
        refreshDogs()
    }

    // Handle actions for each refreshing situation,
    // include load data for the first time when open the app
    // The reason why internal fun is because refreshDogs need to be used
    // everytime when refreshing or load more data
    internal fun refreshDogs() {
        currentPage = 0
        isLastPage = false
        _state.value = _state.value.copy(
            isRefreshing = true,
            isLoading = false,
            error = ""
        )
        fetchDogs()
    }

    // Handle actions for each loading situation
    // The reason why internal fun is because loadMoreDogs need to be used
    // everytime when refreshing or load more data
    internal fun loadMoreDogs() {
        if (_state.value.isLoading || _state.value.isRefreshing || isLastPage) return
        _state.value = _state.value.copy(isLoading = true)
        fetchDogs()
    }

    // Main function to get dogs data
    private fun fetchDogs() {
        getDogsUseCase(currentPage).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    val newDogs = result.data ?: emptyList()
                    if (newDogs.isEmpty()) {
                        isLastPage = true
                    }
                    val currentList = if (_state.value.isRefreshing) {
                        // Refreshing data, empty existing data list
                        emptyList()
                    } else {
                        // If loading more, append data on existing data list
                        _state.value.dogs
                    }
                    // Refreshing loading states
                    _state.value = _state.value.copy(
                        dogs = currentList + newDogs,
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
                    // set in refreshDogs and loadMoreDogs
                }
            }
        }.launchIn(viewModelScope)
    }

}