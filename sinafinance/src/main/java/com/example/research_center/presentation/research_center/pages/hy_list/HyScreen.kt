package com.example.research_center.presentation.research_center.pages.hy_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.research_center.presentation.Screen
import com.example.research_center.presentation.research_center.pages.hy_list.components.DateTypeHeaderSection
import com.example.research_center.presentation.research_center.pages.hy_list.components.HyBarChart
import com.example.research_center.presentation.research_center.pages.hy_list.components.HyListItem
import com.example.research_center.presentation.research_center.pages.hy_list.components.HyListStickyHeader
import com.example.research_center.presentation.research_center.pages.hy_list.components.SwType

/*
* Hy screen for one subpage of the 研报中心 page
* */
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HyListScreen(
    navController: NavController,
    viewModel: HyListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    val dateType = viewModel.currentDateType

    // PullToRefresh 状态
    val pullToRefreshState = rememberPullToRefreshState()

    // 用一个 LazyListState 来控制滚动
    val listState = rememberLazyListState()

    Box(modifier = Modifier
        .fillMaxWidth()
    ) {
        PullToRefreshBox(
            isRefreshing = state.isRefreshing,
            onRefresh = {
                viewModel.refresh()
            },
            state = pullToRefreshState
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    DateTypeHeaderSection(
                        currentDateType = dateType,
                        onDateTypeSelected = { newType ->
                            viewModel.dateType(newType)
                        }
                    )
                }

                item {
                    SwType(
                        currentSwType = viewModel.currentSwType,
                        onClick = {
                            // 循环切换: 1 -> 2 -> 3 -> 1
                            val nextType = when (viewModel.currentSwType) {
                                1 -> 2
                                2 -> 3
                                else -> 1
                            }
                            viewModel.swType(nextType)
                        }
                    )
                }

                item {
                    HyBarChart(
                        data = state.barChart
                    )
                }

                stickyHeader {
                    HyListStickyHeader(
                        currentSortType = viewModel.currentSortType.value,
                        onSortChanged = { col, typeOrNone ->
                            viewModel.setSort(col, typeOrNone)
                        }
                    )
                }

                // Main list
                itemsIndexed(state.hy) { index, hy ->
                    HyListItem(
                        hy = hy,
                        onItemClick = {
                            navController.navigate(
                                Screen.HyReportScreen.route + "/${hy.code}/${hy.name}"
                            )
                        }
                    )
                    // Load more when reach bottom of the list
                    if (!state.isEndReached && index == state.hy.lastIndex) {
                        viewModel.loadMore()
                    }
                }

                // Footer
                item {
                    // If There's more data and not refreshing
                    if (!state.isEndReached) {
                        if (!state.isRefreshing) {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    // If no more data
                    } else {
                        Text(
                            text = "没有更多数据",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        // 如果有错误信息
        if (state.error.isNotBlank()) {
            Box(
                modifier = Modifier
                    .background(
                        color = Color.Black.copy(alpha = 0.8f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .align(Alignment.TopCenter)
            ) {
                Text(
                    text = state.error,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        }
    }
}