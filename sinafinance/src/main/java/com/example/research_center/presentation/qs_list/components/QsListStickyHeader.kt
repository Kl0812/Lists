package com.example.research_center.presentation.qs_list.components

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.research_center.common.Constants.SINA_GRAY
import com.example.research_center.listUtils.SortText

@Composable
fun QsListStickyHeader(
    currentSortCol: String,
    currentSortType: Int,
    onSortChanged: (String, Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(SINA_GRAY))
            .padding(horizontal = 12.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 左侧: "券商机构"
        Text(
            text = "券商机构",
            color = Color.Gray,
            fontSize = 12.sp,
            modifier = Modifier.weight(1f),
        )

        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.CenterEnd
        ) {
            SortText(
                label = "研报数量",
                isSelected = (currentSortCol == "num"),
                currentSortType = currentSortType,
                sortCol = "num",
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
                label = "推荐股票平均涨幅",
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
    name = "QsListStickHeader Preview",
    showBackground = true
)
@Composable
fun QsListStickHeaderPreview() {
    QsListStickyHeader(
        currentSortCol = "percent",
        currentSortType = 1,
        onSortChanged = { _, _ -> }
    )
}