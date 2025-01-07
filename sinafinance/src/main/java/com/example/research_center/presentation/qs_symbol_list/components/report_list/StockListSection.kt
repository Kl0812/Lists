package com.example.research_center.presentation.qs_symbol_list.components.report_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.research_center.domain.model.Stock
import com.example.research_center.presentation.Screen
import com.example.research_center.presentation.qs_symbol_list.QsSymbolListViewModel
import com.example.research_center.presentation.stock_list.components.StockListItem

@Composable
fun StockListSection(
    items: List<Stock>,
    isRefreshing: Boolean,
    isEndReached: Boolean,
    loadMore: () -> Unit,
    navController: NavController
) {

    Text(
        text = "研报列表",
        modifier = Modifier
            .padding(16.dp),
        fontWeight = FontWeight.Bold
    )

    Column() {
        items.forEachIndexed { index, stock: Stock ->
            StockListItem(
                stock = stock,
                onItemClick = {
                    navController.navigate(
                        Screen.StockDetailScreen.route + "/${stock.report_id}"
                    )
                }
            )

            if (!isEndReached && index == items.lastIndex) {
                loadMore()
            }
        }

        if (!isEndReached) {
            if (!isRefreshing) {
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