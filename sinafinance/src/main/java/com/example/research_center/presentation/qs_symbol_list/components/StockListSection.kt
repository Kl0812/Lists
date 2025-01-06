package com.example.research_center.presentation.qs_symbol_list.components

import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable

fun StockListSection() {
    LazyColumn() {
        item {
            Text(
                text = "研报列表",
                modifier = Modifier
                    .padding(vertical = 8.dp)
            )
        }
    }
}
    /*items: List<T>,
    content: @Composable (T) -> Unit,
    isRefreshing: Boolean,
    isEndReached: Boolean,
    loadMore: () -> Unit,
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState()
) {
    LazyColumn(
        state = lazyListState,

    ) {
        item {
            Text(
                text="研报列表",
                modifier= Modifier
                    .padding(vertical=8.dp)
            )
        }
        // TODO: main content items
        items(items.size) { index ->
            content(items[index])
            if (!isEndReached && index == items.lastIndex) {
                loadMore()
            }
        }

        // Footer
        item {
            // If There's more data and not refreshing
            if (!isEndReached && !isRefreshing) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
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

}*/