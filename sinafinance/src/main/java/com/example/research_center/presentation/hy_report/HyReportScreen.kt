package com.example.research_center.presentation.hy_report

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.research_center.listUtils.CustomTopBar
import com.example.research_center.listUtils.ReusableLazyColumn
import com.example.research_center.presentation.Screen
import com.example.research_center.presentation.qs_symbol_list.QsSymbolListViewModel
import com.example.research_center.presentation.research_center.pages.report_list.components.ReportListItem
import com.example.research_center.presentation.research_center.pages.report_list.components.ReportListMenu

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HyReportScreen(
    navController: NavController,
    viewModel: HyReportViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    val hyName = viewModel.hyName ?: "未知行业"

    Scaffold(
        containerColor = Color.White,
        topBar = {
            CustomTopBar(
                title = hyName,
                showReturnButton = true,
                onReturnClick = {
                    navController.popBackStack()
                }
            )
        }
    ) { innerPadding ->

        fun onMenuSelected(menu: String) {
            val rating_change = when(menu) {
                "上调" -> 1
                "下调" -> 3
                "维持" -> 2
                "首次" -> 4
                else -> 0
            }
            viewModel.ratingChange(rating_change = rating_change)
        }

        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {

            ReportListMenu(
                onMenuSelected = ::onMenuSelected,
                currentRating = viewModel.currentRatingChange
            )

            Box(modifier = Modifier
                .fillMaxWidth()
            ) {
                ReusableLazyColumn(
                    items = state.reportList,
                    isRefreshing = state.isRefreshing,
                    isEndReached = state.isEndReached,
                    refresh = { viewModel.refresh() },
                    loadMore = { viewModel.loadMore() },
                    content = { report ->
                        ReportListItem(
                            report = report,
                            onItemClick = {
                                navController.navigate(
                                    Screen.ReportDetailScreen.route + "/${report.report_id}"
                                )
                            }
                        )
                    }
                )

                if (state.error.isNotBlank()) {
                    Text(
                        text = state.error,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(20.dp)
                    )
                }
            }
        }
    }
}