package com.example.research_center.presentation.hy_list.components

import android.annotation.SuppressLint
import android.icu.text.DecimalFormat
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.research_center.common.Constants.SINA_BLUE
import com.example.research_center.common.Constants.SINA_GREEN
import com.example.research_center.domain.model.Hy
import com.example.research_center.domain.model.Qs

@SuppressLint("NewApi")
@Composable
fun HyListItem(
    hy: Hy,
    onItemClick: (Hy) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onItemClick(hy)
            }
            .padding(top = 12.dp, start = 16.dp, end = 16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom
        ) {

            Text(
                text = hy.name,
                fontSize = 16.sp,
                modifier = Modifier.weight(1f),
            )

            Text(
                text = hy.num.toString(),
                fontSize = 16.sp,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End,
                color = Color(SINA_BLUE)
            )

            Text(
                text = "${hy.symbol_name}(${hy.symbol_num})",
                fontSize = 16.sp,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End
            )

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
    name = "HyListItem Preview",
    showBackground = true
)
@Composable
fun HyListItemPreview() {
    val sampleHy = Hy(
        name = "汽车",
        num = 49,
        code = "hy0280000",
        symbol_name = "宇通客车",
        symbol_num = 5,
    )

    HyListItem(
        hy = sampleHy,
        onItemClick = {}
    )
}