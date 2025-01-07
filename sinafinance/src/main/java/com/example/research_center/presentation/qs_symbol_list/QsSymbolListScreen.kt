package com.example.research_center.presentation.qs_symbol_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import com.example.research_center.listUtils.CustomTopBar
import com.example.research_center.presentation.qs_symbol_list.components.header.DateTypeHeaderSection
import com.example.research_center.presentation.qs_symbol_list.components.recent_cover.QsSymbolListSection
import com.example.research_center.presentation.qs_symbol_list.components.report_list.StockListSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QsSymbolListScreen(
    navController: NavController,
    viewModel: QsSymbolListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    val qsName = viewModel.qsCode ?: "未知券商"

    val pullToRefreshState = rememberPullToRefreshState()
    val lazyListState: LazyListState = rememberLazyListState()
    val dateType = viewModel.currentDateType

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
                                .background(Color(0xFFF0F0F0))
                        )
                    }

                    item {
                        QsSymbolListSection(
                            qsName = qsName,
                            viewModel = viewModel
                        )
                    }

                    item {
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(12.dp)
                                .background(Color(0xFFF0F0F0))
                        )
                    }

                    item {
                        StockListSection()
                        // TODO: 传 viewModel for pagination
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