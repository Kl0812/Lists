package com.example.research_center.presentation.research_center.pages.hy_list.components

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
* Customised hy list sticky header component
* */
@Composable
fun HyListStickyHeader(
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
        Text(
            text = "名称",
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
                isSelected = true,
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
            Text(
                text = "明星个股",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(
    name = "HyListStickyHeader Preview",
    showBackground = true
)
@Composable
fun HyListStickyHeaderPreview() {
    HyListStickyHeader(
        currentSortType = 1,
        onSortChanged = { _, _ -> }
    )
}