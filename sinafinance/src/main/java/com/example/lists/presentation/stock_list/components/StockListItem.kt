package com.example.lists.presentation.stock_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.lists.domain.model.Stock

/*
* This file is to set the elements in each item in the LazyVerticalStaggeredGrid
* There's an image as the element, wrapped by a clickable box.
* */
@Composable
fun StockListItem(
    stock: Stock,
    onItemClick: (Stock) -> Unit
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clickable { onItemClick(stock) }
                .padding(top = 12.dp, start = 16.dp, end = 16.dp)
        ) {
            Column(modifier = Modifier.wrapContentSize()) {
                Text(
                    text = stock.title,
                    fontSize = 16.sp,
                    style = LocalTextStyle.current.merge(
                        TextStyle(
                            lineHeight = 1.8.em,
                            platformStyle = PlatformTextStyle(
                                includeFontPadding = false
                            ),
                            lineHeightStyle = LineHeightStyle(
                                alignment = LineHeightStyle.Alignment.Center,
                                trim = LineHeightStyle.Trim.Both
                            )
                        )
                    )
                )
                Spacer(
                    modifier = Modifier.padding(top = 4.dp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stock.media_source,
                        fontSize = 12.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                    // TODO: Add time logic
                    Text(
                        text = stock.create_date,
                        fontSize = 12.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
                HorizontalDivider(
                    thickness = 0.2.dp,
                    color = Color.Gray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )
            }
        }
}

@Preview(
    name = "StockListItem Preview",
    showBackground = true
)
@Composable
fun StockListItemPreview() {
    val sampleStock = Stock(
        create_date = "2024-12-17",
        create_time = "09:11:02",
        media_source = "东北证券",
        title = "中心通讯(000000)：坚持“链家+算力”主航道 AI终端全系布局",
        wapUrl = "https://test.com"
    )

    // 当预览时，onItemClick可以使用空的lambda
    StockListItem(
        stock = sampleStock,
        onItemClick = {}
    )
}