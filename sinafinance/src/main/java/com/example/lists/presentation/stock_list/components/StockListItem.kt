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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
                .padding(16.dp)
        ) {
            Column {
                Text(
                    text = stock.title,
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row( horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = stock.media_source,
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                    // TODO: Add time logic
                    Text(
                        text = stock.create_date,
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
            }
        }
        HorizontalDivider(
            thickness = 1.dp,
            color = Color.Gray,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)

        )

}

// By using AsyncImage, the height of the image can be different.
/*
@Composable
private fun StockListImage(stock: Stock) {
    AsyncImage(
        model = stock.url,
        contentScale = ContentScale.Fit,
        contentDescription = null,
        error = painterResource(R.drawable.no_image),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    )
}*/
