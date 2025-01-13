package com.example.research_center.presentation.research_center.pages.hy_list.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.research_center.listUtils.DateTypeSelector

@Composable
fun DateTypeHeaderSection(
    currentDateType: Int,
    onDateTypeSelected: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        DateTypeSelector(
            currentDateType = currentDateType,
            onDateTypeSelected = onDateTypeSelected
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(
    name = "DateTypeHeaderSection Preview",
    showBackground = true
)
@Composable
fun DateTypeHeaderSectionPreview() {
    DateTypeHeaderSection(
        currentDateType = 2,
        onDateTypeSelected = {
        }
    )
}