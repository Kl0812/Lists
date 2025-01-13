package com.example.research_center.presentation.research_center.pages.qs_list.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.research_center.listUtils.CustomCheckBox
import com.example.research_center.listUtils.DateTypeSelector

/*
* This file is to create list menu header
* */
@Composable
fun QsListMenu(
    // For date type
    currentDateType: Int,
    onDateTypeSelected: (Int) -> Unit,

    // For CustomCheckBox
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(12.dp)
        ) {
            DateTypeSelector(
                currentDateType = currentDateType,
                onDateTypeSelected = onDateTypeSelected
            )

            CustomCheckBox(
                isChecked = isChecked,
                onCheckedChange = onCheckedChange,
                label = "头部券商"
            )

        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(
    name = "ReportListItem Preview",
    showBackground = true
)
@Composable
fun QsListMenuPreview() {
    QsListMenu(
        currentDateType = 1,
        onDateTypeSelected = {
        },
        isChecked = false,
        onCheckedChange = {
        }
    )
}
