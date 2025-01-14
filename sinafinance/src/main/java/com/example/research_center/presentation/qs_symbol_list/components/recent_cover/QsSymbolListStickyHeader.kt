package com.example.research_center.presentation.qs_symbol_list.components.recent_cover

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.research_center.common.Constants.SINA_GRAY
import com.example.research_center.listUtils.SortText

/*
* Customised 近期覆盖股票 section sticky header
* This sticky header does not contain a real compose stick header,
* because this header is only used for 近期覆盖股票 section
* */
@Composable
fun QsSymbolListStickyHeader(
    currentSortCol: String,
    currentSortType: Int,
    onSortChanged: (String, Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(SINA_GRAY))
            .padding(horizontal = 16.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 左侧: "券商机构"
        Text(
            text = "股票名称",
            color = Color.Gray,
            fontSize = 12.sp,
            modifier = Modifier.weight(1f),
        )

        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.CenterEnd
        ) {
            SortText(
                label = "最新覆盖日期",
                isSelected = (currentSortCol == "date"),
                currentSortType = currentSortType,
                sortCol = "date",
                onSortChanged = { col, typeOrNone ->
                    onSortChanged(col, typeOrNone)
                }
            )
        }

        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.CenterEnd
        ) {
            SortText(
                label = "覆盖后涨幅",
                isSelected = (currentSortCol == "percent"),
                currentSortType = currentSortType,
                sortCol = "percent",
                onSortChanged = { col, typeOrNone ->
                    onSortChanged(col, typeOrNone)
                }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(
    name = "QsSymbolListStickyHeader Preview",
    showBackground = true
)
@Composable
fun QsSymbolListStickyHeaderPreview() {
    QsSymbolListStickyHeader(
        currentSortCol = "date",
        currentSortType = 0,
        onSortChanged = { _, _ -> }
    )
}