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
        getDogs()
    }

    // Handle actions for each loading situation
    // The reason why internal fun is because getDogs need to be used
    // everytime when refreshing or load more data
    internal fun getDogs() {
        if (_state.value.isLoading || isLastPage) return
        _state.value = _state.value.copy(isLoading = true)

        getDogsUseCase(currentPage).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    val newDogs = result.data ?: emptyList()
                    if (newDogs.isEmpty()) {
                        isLastPage = true
                    }
                    _state.value = _state.value.copy(
                        dogs = _state.value.dogs + newDogs,
                        isLoading = false
                    )
                    currentPage++
                    _state.value = DogListState(dogs = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = DogListState(
                        error = result.message ?: "An Unexpected Error Occur")
                }
                is Resource.Loading -> {
                    _state.value = DogListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

}