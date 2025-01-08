package com.example.research_center.presentation.report_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.research_center.listUtils.ReusableLazyColumn
import com.example.research_center.presentation.Screen
import com.example.research_center.presentation.report_list.components.ReportListItem
import com.example.research_center.presentation.report_list.components.ReportListMenu

@Composable
fun ReportListScreen(
    navController: NavController,
    viewModel: ReportListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

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
        modifier = Modifier.fillMaxSize()
    ) {

        ReportListMenu(
            onMenuSelected = ::onMenuSelected,
            currentRating = viewModel.currentRatingChange
        )

        Box(modifier = Modifier
            .fillMaxWidth()
        ) {
            ReusableLazyColumn(
                items = state.report,
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