package com.example.research_center.presentation.qs_symbol_list.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.research_center.presentation.qs_symbol_list.QsSymbolListViewModel

@Composable
fun QsSymbolListSection(
    qsName: String,
    viewModel: QsSymbolListViewModel
) {
    // Title
    Text(
        text = "近期覆盖股票",
        modifier = Modifier.padding(vertical=8.dp)
    )

    // Header
    QsSymbolListStickyHeader(
        currentSortCol = viewModel.currentSortCol.value, // TODO
        currentSortType = viewModel.currentSortType.value, // TODO
        onSortChanged = { col, typeOrNone ->
            viewModel.setSort(col, typeOrNone)
        }
    )

    // Main content
    var isExpanded by remember { mutableStateOf(false) }
    val symbolList = listOf("股票A", "股票B","股票C","股票D","股票E") // TODO: placeholder

    // if symbolList.size>4 && !isExpanded, only display 4 items
    val displayList = if(!isExpanded && symbolList.size>4) symbolList.take(4) else symbolList

    Column {
        displayList.forEach { stockName ->
            Text(text=stockName, modifier=Modifier.padding(4.dp))
        }
        // "查看更多"
        if(symbolList.size > 4) {
            if(!isExpanded) {
                Text(
                    text="查看更多",
                    modifier=Modifier.padding(4.dp),
                    // onClick => isExpanded=true
                )
            } else {
                Text(
                    text="收起更多",
                    modifier=Modifier.padding(4.dp),
                    // onClick => isExpanded=false
                )
            }
        }
    }
}
