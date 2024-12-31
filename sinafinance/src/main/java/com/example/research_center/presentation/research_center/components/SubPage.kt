package com.example.research_center.presentation.research_center.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun SubPage(
    text: String,
    currentPage: Int,
    myPage: Int,
    scope: CoroutineScope,
    pagerState: PagerState
) {
    Column(
        modifier = Modifier
            .clickable {
                scope.launch {
                    pagerState.animateScrollToPage(myPage)
                }
            }
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier
                .padding(top = 8.dp, bottom = 6.dp)
        )
        Box(
            modifier = Modifier
                .height(3.dp)
                .width(50.dp)
                .background(
                    if (currentPage == myPage) Color(0xFF036BFC) else Color.Transparent
                )
        )
    }
}