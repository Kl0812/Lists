package com.example.lists.listUtils

import androidx.compose.foundation.lazy.LazyListState

internal fun LazyListState.reachBottom(buffer: Int = 0): Boolean {
    val lastVisibleItem = this.layoutInfo.visibleItemsInfo.lastOrNull()
    return lastVisibleItem?.index != 0 && lastVisibleItem?.index == this.layoutInfo.totalItemsCount - buffer
}