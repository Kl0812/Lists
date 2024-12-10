package com.example.lists.presentation.dog_list

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.lists.presentation.Screen
import com.example.lists.presentation.dog_list.components.DogListItem
import com.example.lists.presentation.dog_list.components.rememberPullToRefreshState
import kotlinx.coroutines.launch

/*
* This file is used to display the Dog List Screen of the app.
* It is also the entry screen of the app currently.
* */
@Composable
fun DogListScreen(
    navController: NavController,
    viewModel: DogListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val pullToRefreshState = rememberPullToRefreshState(
        onRefresh = { viewModel.getDogs() },
        isRefreshing = state.isRefreshing
    )

    val listState = rememberLazyStaggeredGridState()
    val refreshThreshold = with(LocalDensity.current) { 100.dp.toPx() }

    val offsetAnim = remember { androidx.compose.animation.core.Animatable(pullToRefreshState.offsetY) }
    val coroutineScope = rememberCoroutineScope()

    // When user dragging, setY will change, snap the animation to that value
    LaunchedEffect(pullToRefreshState.offsetY) {
        offsetAnim.snapTo(pullToRefreshState.offsetY)
    }

    // When refreshing completes, animate back to zero
    LaunchedEffect(state.isRefreshing) {
        if (!state.isRefreshing) {
            coroutineScope.launch {
                offsetAnim.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(durationMillis = 300, easing = LinearEasing)
                )
                pullToRefreshState.reset()
            }
        }
    }

    // Detect gesture and handle pointer events
    val dragModifier = Modifier.pointerInput(state.isRefreshing) {
        if (!state.isRefreshing) {
            detectVerticalDragGestures(
                onVerticalDrag = { _, dragAmount ->
                    val firstVisibleItemIndex = listState.layoutInfo.visibleItemsInfo
                        .firstOrNull()?.index ?: 0
                    if (firstVisibleItemIndex == 0 && dragAmount > 0) {
                        pullToRefreshState.offsetY = (pullToRefreshState.offsetY + dragAmount)
                            .coerceAtLeast(0f)
                    }
                },
                onDragEnd = {
                    if (pullToRefreshState.offsetY > refreshThreshold) {
                        pullToRefreshState.onRefresh()
                    } else {
                        if (!pullToRefreshState.isRefreshing) {
                            coroutineScope.launch {
                                offsetAnim.animateTo(
                                    targetValue = 0f,
                                    animationSpec = tween(
                                        durationMillis = 300,
                                        easing = LinearEasing
                                    )
                                )
                                pullToRefreshState.reset()
                            }
                        }
                    }
                }
            )
        }
    }

    // Main container for the whole screen
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFf7cd60))
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        // If load for the first time or refresh the page, show loading animation
        if (state.isLoading && state.dogs.isEmpty()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            // Show the main content
            Column(
                modifier = Modifier
                    .offset { IntOffset(x = 0, y = offsetAnim.value.toInt()) }
                    .fillMaxSize()
            ) {
                // Header for the grid, show loading animation and information
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(top = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    when {
                        pullToRefreshState.isRefreshing -> {
                            CircularProgressIndicator()
                        }
                        pullToRefreshState.offsetY > 0f -> {
                            Text("Keep pulling to refresh...")
                        }
                    }
                }

                // Use LazyVerticalStaggeredGrid to show images with different height
                // to make the screen looks like a waterfall
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

                        // TODO: Add a footer for load more if desired
                    }
                )

                // TODO: Add logic for load-more if needed
            }
        }

        // Actions if app state is error
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
}

// item {
//     if (state.isLoadingMore) {
//         // Show loading indicator for load-more scenario
//         CircularProgressIndicator(
//             modifier = Modifier
//                 .fillMaxWidth()
//                 .padding(16.dp)
//                 .wrapContentSize(Alignment.Center)
//         )
//     } else if (/* condition to show pull-up hint */) {
//         Text(
//             text = "Pull up to load more...",
//             textAlign = TextAlign.Center,
//             modifier = Modifier
//                 .fillMaxWidth()
//                 .padding(16.dp)
//         )
//     }
// }

/*// Pull up and load more
LaunchedEffect(listState) {
    snapshotFlow {
        listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
    }.collect { lastVisibleItemIndex ->
        // If the user has scrolled to the last item, we can load more data automatically
        // or show a prompt. For manual "pull up" gestures, you'd need a custom approach,
        // but infinite scroll is often handled this way.
        if (lastVisibleItemIndex == state.dogs.lastIndex && !state.isLoadingMore) {
            viewModel.getDogs()
        }
    }
}*/