package com.example.research_center.presentation.qs_symbol_list.components.recent_cover

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.research_center.common.Constants.SINA_BLUE
import com.example.research_center.domain.model.QsSymbol
import com.example.research_center.presentation.Screen
import com.example.research_center.presentation.qs_symbol_list.QsSymbolListViewModel

// Main component for 近期覆盖股票 section
@Composable
fun QsSymbolListSection(
    qsName: String, // can be used for route later
    viewModel: QsSymbolListViewModel,
    isExpanded: Boolean,
    onExpandChanged: (Boolean) -> Unit,
    navController: NavController
) {
    // Title
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "近期覆盖股票",
            fontWeight = FontWeight.Bold
        )
    }

    // Header
    QsSymbolListStickyHeader(
        currentSortCol = viewModel.currentSortCol.value,
        currentSortType = viewModel.currentSortType.value,
        onSortChanged = { col, typeOrNone ->
            viewModel.setSort(col, typeOrNone)
        }
    )

    // Main content
    val symbolList = viewModel.state.value.qsSymbol

    // if symbolList.size>4 && !isExpanded, only display 4 items
    val displayList = if(!isExpanded && symbolList.size>4) {
        symbolList.take(4)
    } else {
        symbolList
    }

    Column {
        displayList.forEach { item: QsSymbol ->
            QsSymbolListItem(
                qsSymbol = item,
                onItemClick = {
                    navController.navigate(
                        Screen.StockScreen.route + "/${item.code}"
                    )
                }
            )
        }

        // 查看更多/收起更多
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            if (symbolList.size > 4) {
                if (!isExpanded) {
                    Text(
                        text = "查看更多",
                        modifier = Modifier
                            .clickable {
                                onExpandChanged(true)
                            },
                        color = Color(SINA_BLUE)
                    )
                } else {
                    Text(
                        text = "收起更多",
                        modifier = Modifier
                            .clickable {
                                onExpandChanged(false)
                            },
                        color = Color(SINA_BLUE)
                    )
                }
            }
        }
    }
}
