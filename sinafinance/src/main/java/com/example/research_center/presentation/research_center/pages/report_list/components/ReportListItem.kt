package com.example.research_center.presentation.research_center.pages.report_list.components

import android.annotation.SuppressLint
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.research_center.domain.model.Report
import com.example.research_center.listUtils.ShowDate

/*
* Customised report list item component
* */
@SuppressLint("NewApi")
@Composable
fun ReportListItem(
    report: Report,
    onItemClick: (Report) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onItemClick(report)
            }
            .padding(top = 12.dp, start = 16.dp, end = 16.dp)
    ) {
        Text(
            text = report.title,
            fontSize = 16.sp,
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = report.orgname,
                fontSize = 12.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )

            ShowDate(report.adddate)
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
    name = "ReportListItem Preview",
    showBackground = true
)
@Composable
fun ReportListItemPreview() {
    val sampleReport = Report(
        adddate = "2024-12-26 00:00:00",
        orgname = "东吴证券",
        report_id = "788525455904",
        title = "宇邦新材(301266)：焊带加工费下滑短期承压 BC新品有望结构性改善盈利"
    )

    // 当预览时，onItemClick可以使用空的lambda
    ReportListItem(
        report = sampleReport,
        onItemClick = {}
    )
}