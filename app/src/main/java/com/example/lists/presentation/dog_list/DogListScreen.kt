package com.example.lists.presentation.dog_list

import android.graphics.Paint.Align
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.lists.presentation.Screen
import com.example.lists.presentation.dog_list.components.DogListItem
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull

@OptIn(FlowPreview::class)
@Composable
fun DogListScreen(
    navController: NavController,
    viewModel: DogListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val listState = rememberLazyStaggeredGridState()
    val overscrollState = remember { mutableStateOf(false)}

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFf7cd60))
        .nestedScroll(object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                if (available.y < 0 &&
                    listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == state.dogs.lastIndex
                ) {
                    overscrollState.value = true
                }
                return Offset.Zero
            }

            override fun onPostScroll(consumed: Offset, available: Offset, source: NestedScrollSource): Offset {
                if (overscrollState.value && available.y == 0f && !state.isLoading) {
                    viewModel.getDogs()
                    overscrollState.value = false
                }
                return Offset.Zero
            }
        })
    ) {
        if (state.isLoading && state.dogs.isEmpty()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            LazyVerticalStaggeredGrid(
                state = listState,
                columns = StaggeredGridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                content = {
                    items(state.dogs) { dog ->
                        DogListItem(
                            dog = dog,
                            onItemClick = {
                                navController.navigate(
                                    Screen.DogDetailScreen.route + "/${dog.id}"
                                )
                            }
                        )
                    }
                    item {
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .wrapContentSize(Alignment.Center)
                            )
                        } else {
                            Text(
                                text = "Pull up to load more...",
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            )
                        }
                    }
                }
            )
        }
        if (state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }

    }

    /*LaunchedEffect(listState) {
        snapshotFlow {
            listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
        }
            .filterNotNull()
            .distinctUntilChanged()
            .collect { lastVisibleIndex ->
            Log.d("DogListScreen", "Last visible index: $lastVisibleIndex")
            *//*if (lastVisibleIndex != null &&
                lastVisibleIndex == state.dogs.lastIndex && !state.isLoading) {
                viewModel.getDogs()
            }*//*
            if (lastVisibleIndex != null) {
                Log.d("DogListScreen", "Last visible index: $lastVisibleIndex")
            }

            if (lastVisibleIndex != null && lastVisibleIndex == state.dogs.lastIndex && !state.isLoading) {
                Log.d("DogListScreen", "Fetching more dogs...")
                viewModel.getDogs()
            }
        }
    }*/
}