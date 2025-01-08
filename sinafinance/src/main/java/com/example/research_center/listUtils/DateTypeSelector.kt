package com.example.research_center.listUtils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DateTypeSelector(
    currentDateType: Int,
    onDateTypeSelected: (Int) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // 近一周
        SelectableBox(
            text = "近一周",
            isSelected = (currentDateType == 1),
            onClick = { onDateTypeSelected(1) }
        )


        // 近一月
        SelectableBox(
            text = "近一月",
            isSelected = (currentDateType == 2),
            onClick = { onDateTypeSelected(2) }
        )

        // 近三月
        SelectableBox(
            text = "近三月",
            isSelected = (currentDateType == 3),
            onClick = { onDateTypeSelected(3) }
        )
    }
}

@Composable
fun SelectableBox(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = Color(0xFFF0F0F0)

    // Change color is selected
    val borderColor = if (isSelected) Color(0xFF036BFC) else Color.Gray
    val textColor   = if (isSelected) Color(0xFF036BFC) else Color.Gray

    Box(
        modifier = Modifier
            .border(0.2.dp, borderColor, shape = RoundedCornerShape(1.dp))
            .background(backgroundColor, shape = RoundedCornerShape(1.dp))
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 12.sp
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(
    name = "ReportListItem Preview",
    showBackground = true
)
@Composable
fun DateTypeSelectorPreview() {
    DateTypeSelector(1,
        onDateTypeSelected = { newType ->

        }
    )
}
