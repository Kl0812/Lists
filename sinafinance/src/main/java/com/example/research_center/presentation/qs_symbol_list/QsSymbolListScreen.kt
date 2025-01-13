package com.example.research_center.presentation.qs_symbol_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.research_center.common.Constants.SINA_GRAY
import com.example.research_center.listUtils.CustomTopBar
import com.example.research_center.presentation.Screen
import com.example.research_center.presentation.qs_symbol_list.components.header.DateTypeHeaderSection
import com.example.research_center.presentation.qs_symbol_list.components.recent_cover.QsSymbolListSection
import com.example.research_center.presentation.research_center.pages.report_list.components.ReportListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QsSymbolListScreen(
    navController: NavController,
    viewModel: QsSymbolListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val pullToRefreshState = rememberPullToRefreshState()
    val lazyListState: LazyListState = rememberLazyListState()

    val dateType = viewModel.currentDateType
    val qsName = viewModel.qsCode ?: "未知券商"
    var isExpanded by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            CustomTopBar(
                title = "${qsName}研报",
                showReturnButton = true,
                onReturnClick = {
                    navController.popBackStack()
                }
            )
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
        ) {
            PullToRefreshBox(
                isRefreshing = state.isRefreshing,
                onRefresh = { viewModel.refresh() },
                state = pullToRefreshState,
                modifier = Modifier
                    .fillMaxSize()
            ) {

                LazyColumn(
                    state = lazyListState,
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
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(12.dp)
                                .background(Color(SINA_GRAY))
                        )
                    }

                    item {
                        QsSymbolListSection(
                            qsName = qsName,
                            viewModel = viewModel,
                            isExpanded = isExpanded,
                            onExpandChanged = { expanded ->
                                isExpanded = expanded
                            },
                            navController = navController
                        )
                    }

                    item {
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(12.dp)
                                .background(Color(SINA_GRAY))
                        )
                    }

                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "研报列表",
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    item {
                        HorizontalDivider(
                            thickness = 0.2.dp,
                            color = Color.Gray,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }

                    itemsIndexed(state.reportList) { index, report ->
                        ReportListItem(
                            report = report,
                            onItemClick = {
                                navController.navigate(
                                    Screen.ReportDetailScreen.route + "/${report.report_id}"
                                )
                            }
                        )
                        // Load more when reach bottom of the list
                        if (!state.isEndReached && index == state.reportList.lastIndex) {
                            viewModel.loadMore()
                        }
                    }

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