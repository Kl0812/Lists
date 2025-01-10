package com.example.research_center.presentation.hy_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.research_center.listUtils.ReusableLazyColumn
import com.example.research_center.presentation.hy_list.components.DateTypeHeaderSection
import com.example.research_center.presentation.hy_list.components.HyBarChart
import com.example.research_center.presentation.hy_list.components.HyListItem
import com.example.research_center.presentation.hy_list.components.HyListStickyHeader
import com.example.research_center.presentation.hy_list.components.SwType

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HyListScreen(
    navController: NavController,
    viewModel: HyListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    val dateType = viewModel.currentDateType
    val swType = viewModel.currentSwType

    Box {
        LazyColumn(
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
                        // ----【修改】循环切换 1->2->3->1
                        val nextType = when (viewModel.currentSwType) {
                            1 -> 1
                            2 -> 2
                            else -> 3
                        }
                        viewModel.swType(type = nextType)
                    }
                )
            }

            item {
                HyBarChart()
            }

            stickyHeader {
                HyListStickyHeader(
                    currentSortType = viewModel.currentSortType.value,
                    onSortChanged = { col, typeOrNone ->
                        viewModel.setSort(col, typeOrNone)
                    }
                )
            }

            item {
                ReusableLazyColumn(
                    items = state.hy,
                    isRefreshing = state.isRefreshing,
                    isEndReached = state.isEndReached,
                    refresh = { viewModel.refresh() },
                    loadMore = { viewModel.loadMore() },
                    content = { item ->
                        HyListItem(
                            hy = item,
                            onItemClick = { } // TODO
                        )
                    }
                )
            }
        }

        if (state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}