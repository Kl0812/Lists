package com.example.lists.presentation.dog_list

import com.example.lists.domain.model.Dog

data class DogListState (
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val dogs: List<Dog> = emptyList(),
    val error: String = ""

)