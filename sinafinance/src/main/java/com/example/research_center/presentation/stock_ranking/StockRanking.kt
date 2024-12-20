package com.example.research_center.presentation.stock_ranking

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.research_center.listUtils.reusableList.ReusableLazyColumn
import com.example.research_center.presentation.Screen
import com.example.research_center.presentation.stock_ranking.components.StockListViewModel
import com.example.research_center.presentation.stock_ranking.components.StockListItem
import com.example.research_center.presentation.stock_ranking.components.StockListMenu


/*
* This file is used to display the Stock List Screen of the app.
* It is also the entry screen of the app currently.
* */
@SuppressLint("NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockRanking(
    navController: NavController,
    viewModel: StockListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        // Menu Bar Header
        StockListMenu()

        // Divider
        HorizontalDivider(
            thickness = 0.2.dp,
            color = Color.Gray,
            modifier = Modifier
                .fillMaxWidth()
        )

        // Main content list
        ReusableLazyColumn(
            items = state.stocks,
            isRefreshing = state.isRefreshing,
            isLastPage = state.isLastPage,
            error = state.error,
            onRefresh = { viewModel.refreshStocks() },
            onLoadMore = { viewModel.loadMoreStocks() },
            listContent = { stock ->
                StockListItem(
                    stock = stock,
                    onItemClick = { selectedStock ->
                        val encodedUrl = Uri.encode(selectedStock.wapUrl)
                        navController.navigate(
                            "${Screen.StockDetailScreen.route}/$encodedUrl"
                        ) {
                            launchSingleTop = true
                        }
                    }
                )
            },
            modifier = Modifier
                .fillMaxSize()
                .zIndex(-1f)
        )
    }
}

