package com.example.research_center.presentation.stock.components

import android.icu.text.DecimalFormat
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.research_center.common.Constants.SINA_GREEN

/*
* Customised component for display a single stock item22
* */
@Composable
fun StockItem(
    title: String,
    value: String,
    ycp: String = ""
) {
    // Float format
    val decimalFormat = DecimalFormat("0.00")

    // Check if value is float
    val myFloat = value.toFloatOrNull()

    // If can not convert to float, means value is the title
    if (myFloat == null) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, start = 16.dp, end = 16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = value,
                    fontSize = 20.sp,
                    textAlign = TextAlign.End,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
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
        return
    }

    // Else: value is a float
    // Separate value in groups, so can display different format
    val displayText: String
    var displayColor: Color = Color.Black

    val ycpFloat = ycp.toFloatOrNull() ?: -1f

    when (title) {
        "最近成交价", "最高成交价", "最低成交价", "今日开盘价" -> {
            // 对比ycp
            displayText = decimalFormat.format(myFloat)
            displayColor = when {
                myFloat  > ycpFloat -> Color.Red
                myFloat  < ycpFloat -> Color(SINA_GREEN)  // 绿色
                else -> Color.Gray
            }
        }

        "成交数量", "成交金额" -> {
            // 除以 10000 后，显示两位小数并加“万”
            val millionValue = myFloat / 10000f
            val text2decimals = decimalFormat.format(millionValue)
            displayText = "$text2decimals 万"
            displayColor = Color.Black
        }

        else -> {
            // 其他数值 => 两位小数 + 黑色
            displayText = decimalFormat.format(myFloat)
            displayColor = Color.Black
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, start = 16.dp, end = 16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                modifier = Modifier.weight(1f),
            )

            Text(
                text = displayText,
                fontSize = 16.sp,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End,
                color = displayColor
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