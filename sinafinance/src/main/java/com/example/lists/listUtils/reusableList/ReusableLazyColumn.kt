package com.example.lists.listUtils.reusableList

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

/*
* This fun make a reusable lazy column,
* with pull down to refresh and
* pull up to end auto load more function
* by using pull to refresh
* */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> ReusableLazyColumn(
    items: List<T>,
    isRefreshing: Boolean,
    isLoading: Boolean,
    isLastPage: Boolean,
    error: String,
    onRefresh: () -> Unit,
    onLoadMore: () -> Unit,
    listContent: @Composable (T) -> Unit,
    modifier: Modifier = Modifier,
    threshold: Int = 5,
    lazyListState: LazyListState = rememberLazyListState()
) {
    val pullToRefreshState = rememberPullToRefreshState()
    var loadMoreTriggered by remember { mutableStateOf(false) }

    Box(modifier = Modifier
        .nestedScroll(pullToRefreshState.nestedScrollConnection)
    ) {
        LazyColumn(
            state = lazyListState,
            modifier = Modifier.fillMaxSize()
        ) {
            items(items) {
                listContent(it)
            }

            // If user pull down to the end and load
            if (isLoading && !isLastPage) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        androidx.compose.material3.CircularProgressIndicator()
                    }
                }
            }
        }

        if(pullToRefreshState.isRefreshing) {
            LaunchedEffect(true) {
                onRefresh()
            }
        }

        LaunchedEffect(isRefreshing) {
            if(isRefreshing) {
                pullToRefreshState.startRefresh()
            } else {
                pullToRefreshState.endRefresh()
            }
        }

        PullToRefreshContainer(
            state = pullToRefreshState,
            modifier = Modifier
                .align(Alignment.TopCenter),
        )

        // Load more logic
        LaunchedEffect(lazyListState) {
            snapshotFlow {
                lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
            }
                .filter { lastVisibleItemIndex ->
                    lastVisibleItemIndex != null &&
                            lastVisibleItemIndex >= (items.size - threshold) &&
                            !isLoading &&
                            !isLastPage &&
                            !loadMoreTriggered &&
                            items.size >= threshold
                }
                .distinctUntilChanged()
                .collect {
                    loadMoreTriggered = true
                    onLoadMore()
                }
        }

        LaunchedEffect(isLoading) {
            if (!isLoading) {
                loadMoreTriggered = false
            }
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