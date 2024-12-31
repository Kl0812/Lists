package com.example.research_center.listUtils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.research_center.presentation.qs_list.components.QsListMenu

@Composable
fun CustomCheckBox(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    label: String
) {
    val scaleMultiplier = 0.7f

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .clickable {
                onCheckedChange(!isChecked)
            }
            .wrapContentSize()
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = null,
            modifier = Modifier
                .scale(scaleMultiplier),
            colors = CheckboxDefaults.colors(
                checkedColor = Color(0xFF036BFC),
                uncheckedColor = Color.Gray,
                checkmarkColor = Color.White,
            )
        )
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(
    name = "StockListItem Preview",
    showBackground = true
)
@Composable
fun CustomCheckBoxPreview() {
    CustomCheckBox(
        isChecked = true,
        onCheckedChange = {},
        label = "头部券商"
    )
}

