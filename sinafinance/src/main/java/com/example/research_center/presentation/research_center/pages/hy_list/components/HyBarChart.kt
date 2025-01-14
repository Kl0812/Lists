package com.example.research_center.presentation.research_center.pages.hy_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.research_center.common.Constants.SINA_BLUE
import com.example.research_center.domain.model.Hy

/*
* Customised bar chart component
* */
@Composable
fun HyBarChart(
    data: List<Hy>
) {
    val chartHeight = 150.dp
    // 研报数量最多的数量
    val maxCount = data.maxOfOrNull { it.num }?.takeIf { it > 0 } ?: 1

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(230.dp)
            .padding(start = 5.dp, end = 5.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {

        data.forEach { item ->
            // 等比呈现柱子高度
            // 计算该柱子的高度比 = item.num / maxCount
            // 实际像素: fraction * 200.dp
            val fraction = (item.num.toFloat() / maxCount.toFloat()).coerceIn(0f, 1f)
            val barHeight = chartHeight * fraction

            // 行业名称如 "汽车零部件" -> 超5字 => "汽车..."
            val shortName = if (item.name.length > 4) {
                item.name.take(2) + "..."
            } else {
                item.name
            }

            // 柱子UI
            Column(
                modifier = Modifier.width(40.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = item.num.toString(),
                    fontSize = 12.sp
                )

                // 柱子的矩形
                Box(
                    modifier = Modifier
                        .width(20.dp)
                        .height(barHeight)
                        .background(Color(SINA_BLUE))
                )

                // 行业名称，倾斜45度
                Box(
                    modifier = Modifier
                        .height(40.dp)
                ) {
                    Text(
                        text = shortName,
                        fontSize = 9.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .rotate(-55f),
                        maxLines = 1
                    )
                }
            }
        }
    }
}