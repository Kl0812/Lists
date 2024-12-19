@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.lists.listUtils.reusableList

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

/*
* This fun make a reusable lazy vertical staggered grid,
* with pull down to refresh and
* pull up to end auto load more function
* by using pull to refresh
* */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> ReusableLazyVerticalStaggeredGrid(
    items: List<T>,
    content: @Composable (T) -> Unit,
    isRefreshing: Boolean,
    isLastPage: Boolean,
    error: String,
    onRefresh: () -> Unit,
    onLoadMore: () -> Unit,
    modifier: Modifier = Modifier,
    lazyStaggeredGridState: LazyStaggeredGridState = rememberLazyStaggeredGridState(),
    threshold: Int = 5
) {
    val pullToRefreshState = rememberPullToRefreshState()

    // Observe list scrolling
    val isLoading: Boolean by remember {
        derivedStateOf {
            val lastVisibleItem = lazyStaggeredGridState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastVisibleItem?.index != 0 && lastVisibleItem?.index ==
                    lazyStaggeredGridState.layoutInfo.totalItemsCount - threshold
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(pullToRefreshState.nestedScrollConnection)
    ) {
        LazyVerticalStaggeredGrid(
            state = lazyStaggeredGridState,
            columns = StaggeredGridCells.Fixed(2),
            modifier = Modifier.fillMaxSize()
        ) {
            items(items) {
                content(it)
            }

            if (isLoading && !isLastPage) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }

        if (pullToRefreshState.isRefreshing) {
            LaunchedEffect(true) {
                onRefresh()
            }
        }

        LaunchedEffect(isRefreshing) {
            if (isRefreshing) {
                pullToRefreshState.startRefresh()
            } else {
                pullToRefreshState.endRefresh()
            }
        }

        PullToRefreshContainer(
            state = pullToRefreshState,
            modifier = Modifier
                .align(Alignment.TopCenter)
        )

        LaunchedEffect(isLoading) {
            if (isLoading) onLoadMore()
        }

        if (error.isNotBlank()) {
            Snackbar(
                action = {
                    TextButton(onClick = onRefresh) {
                        Text("Retry")
                    }
                },
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                Text(text = error)
            }
        }
    }
}