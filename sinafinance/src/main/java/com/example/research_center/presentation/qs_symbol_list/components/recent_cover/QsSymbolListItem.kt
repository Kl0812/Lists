package com.example.research_center.presentation.qs_symbol_list.components.recent_cover

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
import com.example.research_center.common.Constants.SINA_GREEN
import com.example.research_center.domain.model.QsSymbol

@SuppressLint("NewApi")
@Composable
fun QsSymbolListItem(
    qsSymbol: QsSymbol,
    onItemClick: (QsSymbol) -> Unit
) {
    // Transform digital format
    val decimalFormat = DecimalFormat("0.00")
    val percentValue = decimalFormat.format(qsSymbol.percent)
    val prefix = when {
        qsSymbol.percent > 0 -> "+"
        else -> ""
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onItemClick(qsSymbol)
            }
            .padding(top = 12.dp, start = 16.dp, end = 16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = qsSymbol.name,
                fontSize = 16.sp,
                modifier = Modifier.weight(1f),
            )

            Text(
                text = qsSymbol.date,
                fontSize = 16.sp,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End,
                color = Color.Black
            )

            Text(
                text = "$prefix$percentValue%",
                fontSize = 16.sp,
                color = if(qsSymbol.percent > 0) Color.Red
                    else if(qsSymbol.percent == 0f) Color.Gray
                    else Color(SINA_GREEN),
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
    name = "QsListItem Preview",
    showBackground = true
)
@Composable
fun QsSymbolListItemPreview() {
    val sampleQsSymbol = QsSymbol(
        name = "聚灿光电",
        date = "2025-01-01",
        percent = 0f,
        code = "sh600000",
    )

    QsSymbolListItem(
        qsSymbol = sampleQsSymbol,
        onItemClick = {}
    )
}