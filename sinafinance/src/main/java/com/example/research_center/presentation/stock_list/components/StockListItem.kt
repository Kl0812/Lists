package com.example.research_center.presentation.stock_list.components

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.research_center.domain.model.Stock
import com.example.research_center.listUtils.ShowDate

@SuppressLint("NewApi")
@Composable
fun StockListItem(
    stock: Stock,
    onItemClick: (Stock) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onItemClick(stock)
            }
            .padding(top = 12.dp, start = 16.dp, end = 16.dp)
    ) {
        Text(
            text = stock.title,
            fontSize = 16.sp,
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = stock.orgname,
                fontSize = 12.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )

            ShowDate(stock)
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

@RequiresApi(Build.VERSION_CODES.O)
@Preview(
    name = "StockListItem Preview",
    showBackground = true
)
@Composable
fun StockListItemPreview() {
    val sampleStock = Stock(
        adddate = "2024-12-26 00:00:00",
        orgname = "东吴证券",
        report_id = "788525455904",
        title = "宇邦新材(301266)：焊带加工费下滑短期承压 BC新品有望结构性改善盈利"
    )

    // 当预览时，onItemClick可以使用空的lambda
    StockListItem(
        stock = sampleStock,
        onItemClick = {}
    )
}