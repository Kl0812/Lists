@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.lists.presentation.dog_list.components

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.zIndex
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

/*
* This file is used to make a pullable LazyVerticalStaggeredGrid,
* */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> PullToRefreshLazyVerticalStaggeredGrid(
    items: List<T>,
    content: @Composable (T) -> Unit,
    isRefreshing: Boolean,
    isLoading: Boolean,
    onRefresh: () -> Unit,
    onLoadMore: () -> Unit,
    modifier: Modifier = Modifier,
    lazyStaggeredGridState: LazyStaggeredGridState = rememberLazyStaggeredGridState()
) {
    val pullToRefreshState = rememberPullToRefreshState()
    var loadMoreTriggered by remember { mutableStateOf(false) }
    /*
    * A Box container that supports nested scrolling via the nestedScroll modifier.
    * This ensures that when the user pulls down, the pullToRefreshState can intercept
    * and measure the pull gesture before it scrolls the grid.
    * */
    Box(
        modifier = modifier
            .nestedScroll(pullToRefreshState.nestedScrollConnection)
            //Priority: Lowest layer
            .zIndex(1f)
    ) {

        // Use LazyVerticalStaggeredGrid to show images with different height
        // to make the screen looks like a waterfall
        LazyVerticalStaggeredGrid(
            state = lazyStaggeredGridState,
            columns = StaggeredGridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(items) {
                content(it)
            }
        }

        // Launched effect when refresh state is changing
        LaunchedEffect(isRefreshing) {
            if (isRefreshing) {
                pullToRefreshState.startRefresh()
            } else {
                pullToRefreshState.endRefresh()
                loadMoreTriggered = false
            }
        }

        // If the screen is refreshing, call onRefresh function
        LaunchedEffect(pullToRefreshState.isRefreshing) {
            if (pullToRefreshState.isRefreshing && !isRefreshing) {
                onRefresh()
            }
        }

        // Launched effect when reach last item of the list
        LaunchedEffect(lazyStaggeredGridState) {
            snapshotFlow {
                lazyStaggeredGridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
            }
                .filter { lastVisibleItemIndex ->
                    val threshold = 5 // Reach last item threshold
                    val shouldLoadMore = lastVisibleItemIndex != null &&
                            lastVisibleItemIndex >= (items.size - threshold) &&
                            !isLoading &&
                            !loadMoreTriggered &&
                            items.size >= threshold &&
                            items.isNotEmpty()
                    shouldLoadMore
                }
                .distinctUntilChanged()
                .collect { lastVisibleItemIndex ->
                    loadMoreTriggered = true
                    onLoadMore()
                }
        }


        // Reset loading trigger
        LaunchedEffect(isLoading) {
            if (!isLoading) {
                loadMoreTriggered = false
            }
        }

        // A UI element to show pull-down indicator
        PullToRefreshContainer(
            state = pullToRefreshState,
            modifier = Modifier
                .align(Alignment.TopCenter)
        )

    }
}