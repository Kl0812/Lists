package com.example.lists.presentation.dog_details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.Constraints
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lists.common.Constants
import com.example.lists.common.Resource
import com.example.lists.domain.use_case.get_dog.GetDogUseCase
import com.example.lists.domain.use_case.get_dogs.GetDogsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.lang.Error
import javax.inject.Inject

@HiltViewModel
class DogDetailViewModel @Inject constructor(
    private val getDogUseCase: GetDogUseCase,
    savedStateHandle: SavedStateHandle

): ViewModel() {

    private val _state = mutableStateOf(DogDetailState())
    val state : State<DogDetailState> = _state

    init {
        savedStateHandle.get<String>(Constants.PARAM_DOG_ID)?.let { dogId ->
            getDog(dogId)
        }
    }

    private fun getDog(dogId: String) {
        getDogUseCase(dogId).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _state.value = DogDetailState(dog = result.data)
                }
                is Resource.Error -> {
                    _state.value = DogDetailState(
                        error = result.message ?: "An Unexpected Error Occur")
                }
                is Resource.Loading -> {
                    _state.value = DogDetailState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

}