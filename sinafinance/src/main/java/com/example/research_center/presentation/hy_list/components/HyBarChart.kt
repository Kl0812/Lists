package com.example.research_center.presentation.hy_list.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HyBarChart(

) {
    Box(
    modifier = Modifier
        .fillMaxWidth()
        .height(200.dp)
    ) {
        Text(text = "Bar Chart Placeholder", modifier = Modifier)
    }
}