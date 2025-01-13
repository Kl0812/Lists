package com.example.research_center.presentation.research_center.pages.hy_list.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lists.R
import com.example.research_center.common.Constants.SINA_BLUE
import com.example.research_center.listUtils.SortText

@Composable
fun SwType(
    currentSwType: Int,
    onClick: () -> Unit
) {

    val textSize = 16.sp
    val textHeightDp = with(LocalDensity.current) { textSize.toDp() }

    val swTitle = when(currentSwType) {
        1 -> "申万一级"
        2 -> "申万二级"
        3 -> "申万三级"
        else -> "未知"
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "研报覆盖行业前10",
                fontSize = textSize
            )
            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = swTitle,
                fontSize = textSize,
                color = Color(SINA_BLUE),
                modifier = Modifier
                    .clickable { onClick() }
            )
            Spacer(modifier = Modifier.width(10.dp))

            Icon(
                painter = painterResource(R.drawable.swap),
                contentDescription = "arrow triangle down",
                modifier = Modifier
                    .wrapContentSize()
                    .size(textHeightDp),

            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(
    name = "SwType Preview",
    showBackground = true
)
@Composable
fun SwTypePreview() {
    SwType(
        currentSwType = 2,
        onClick = { }
    )
}