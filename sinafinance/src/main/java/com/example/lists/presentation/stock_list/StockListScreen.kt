package com.example.lists.presentation.stock_list

import android.net.Uri
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.lists.listUtils.reusableList.ReusableLazyColumn
import com.example.lists.presentation.Screen
import com.example.lists.presentation.stock_list.components.StockListItem


/*
* This file is used to display the Stock List Screen of the app.
* It is also the entry screen of the app currently.
* */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockListScreen(
    navController: NavController,
    viewModel: StockListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    // Main container for the whole screen
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "研报") },
                // navigationIcon = null // TODO: Currently no upper layer to return
            )
        }
    ) { innerPadding ->

        ReusableLazyColumn(
            items = state.stocks,
            isRefreshing = state.isRefreshing,
            isLoading = state.isLoading,
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
            modifier = Modifier.padding(innerPadding)
        )
    }
}
