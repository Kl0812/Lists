package com.example.lists.presentation.dog_list.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

/*
* This file is used to capture and return the state and pull distance of pulling
* */
class PullToRefreshState(
    val onRefresh: () -> Unit,
    initialRefreshing: Boolean = false
) {
    var isRefreshing by mutableStateOf(initialRefreshing)
    var offsetY by mutableStateOf(0f)

    fun reset() {
        offsetY = 0f
    }
}

@Composable
fun rememberPullToRefreshState(
    onRefresh: () -> Unit,
    isRefreshing: Boolean
): PullToRefreshState {
    val state = remember { PullToRefreshState(onRefresh = onRefresh, initialRefreshing = isRefreshing) }
    LaunchedEffect(isRefreshing) {
        state.isRefreshing = isRefreshing
    }
    return state
}
