package com.example.research_center.presentation.qs_list

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
import com.example.research_center.presentation.qs_list.components.QsListItem
import com.example.research_center.presentation.qs_list.components.QsListMenu
import com.example.research_center.presentation.qs_list.components.QsListStickyHeader
import com.example.research_center.presentation.stock_list.components.StockListItem
import java.net.URLEncoder

@Composable
fun QsListScreen(
    navController: NavController,
    viewModel: QsListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    val dateType = viewModel.currentDateType
    val isTopChecked = (viewModel.currentIsTop == 1)

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        QsListMenu(
            currentDateType = dateType,
            onDateTypeSelected = { newType ->
                viewModel.dateType(newType)
            },
            isChecked = isTopChecked,
            onCheckedChange = { newChecked ->
                viewModel.isTop(newChecked)
            }
        )

        QsListStickyHeader(
            currentSortCol = viewModel.currentSortCol.value,
            currentSortType = viewModel.currentSortType.value,
            onSortChanged = { col, typeOrNone ->
                viewModel.setSort(col, typeOrNone)
            }
        )

        Box(modifier = Modifier
            .fillMaxWidth()
        ) {
            ReusableLazyColumn(
                items = state.qs,
                isRefreshing = state.isRefreshing, // or whatever
                isEndReached = state.isEndReached,
                refresh = { viewModel.refresh() },
                loadMore = { viewModel.loadMore() },
                content = { qs ->
                    QsListItem(
                        qs = qs,
                        onItemClick = {
                            navController.navigate(
                                // 将证券code作为路径传递
                                Screen.QsSymbolScreen.route +
                                        "/${URLEncoder.encode(qs.code, "UTF-8")}"
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