package com.example.research_center.presentation.stock_list

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
import com.example.research_center.presentation.stock_list.components.StockListItem
import com.example.research_center.presentation.stock_list.components.StockListMenu

@Composable
fun StockListScreen(
    navController: NavController,
    viewModel: StockListViewModel = hiltViewModel()
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

        StockListMenu(
            onMenuSelected = ::onMenuSelected,
            currentRating = viewModel.currentRatingChange
        )

        Box(modifier = Modifier
            .fillMaxWidth()
        ) {
            ReusableLazyColumn(
                items = state.stock,
                isRefreshing = state.isRefreshing,
                isEndReached = state.isEndReached,
                refresh = { viewModel.refresh() },
                loadMore = { viewModel.loadMore() },
                content = { stock ->
                    StockListItem(
                        stock = stock,
                        onItemClick = {
                            navController.navigate(
                                Screen.StockDetailScreen.route + "/${stock.report_id}"
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