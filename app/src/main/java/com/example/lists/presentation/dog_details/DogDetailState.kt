package com.example.lists.presentation.dog_details

import com.example.lists.domain.model.DogDetail

data class DogDetailState (
    val isLoading: Boolean = false,
    val dog: DogDetail? = null,
    val error: String = ""

)