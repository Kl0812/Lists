package com.example.lists.presentation.dog_list

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lists.common.Resource
import com.example.lists.domain.model.Dog
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
    val state: State<DogListState> = _state

    private var currentPage = 0
    private var isLastPage = false

    // Initialization function
    init {
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
        Log.d("ViewModel", "Refreshing dogs: isRefreshing=true")
        fetchDogs()
    }

    // Handle actions for each loading situation
    // The reason why internal fun is because loadMoreDogs need to be used
    // everytime when refreshing or load more data
    internal fun loadMoreDogs() {
        if (_state.value.isLoading || _state.value.isRefreshing || isLastPage) return
        _state.value = _state.value.copy(isLoading = true)
        Log.d("ViewModel", "Loading more dogs: isLoading=true")
        fetchDogs()
    }

    // Main function to get dogs data
    private fun fetchDogs() {
        getDogsUseCase(currentPage).onEach { result ->
            Log.d("GetDogsUseCase", "isRefreshing: ${_state.value.isRefreshing}")
            when(result) {
                is Resource.Success -> {
                    Log.d("Success Start", "isRefreshing: ${_state.value.isRefreshing}")
                    val newDogs = result.data ?: emptyList()
                    if (newDogs.isEmpty()) {
                        Log.d("ViewModel", "No more dogs to load: isLastPage=true")
                        isLastPage = true
                    }

                    // According to onRefresh and onLoad, decide how to deal with the data list
                    val updatedDogs = when {
                        _state.value.isRefreshing -> {
                            newDogs
                        }
                        _state.value.isLoading -> {
                            _state.value.dogs + newDogs
                        }
                        else -> {
                            _state.value.dogs + newDogs
                        }
                    }

                    // Refreshing loading states
                    _state.value = _state.value.copy(
                        dogs = updatedDogs,
                        isLoading = false,
                        isRefreshing = false,
                        error = ""
                    )
                    Log.d("Success End", "isRefreshing: ${_state.value.isRefreshing}, dogs size: ${_state.value.dogs.size}")
                    currentPage++
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message ?: "An Unexpected Error Occur",
                        isLoading = false,
                        isRefreshing = false
                    )
                    Log.d("ViewModel", "Fetch Error: ${result.message}")
                }
                is Resource.Loading -> {
                    Log.d("ViewModel", "Fetch Loading")
                    // Do nothing. Because the loading state is
                    // set in refreshDogs and loadMoreDogs
                }
            }
        }.launchIn(viewModelScope)
    }

}