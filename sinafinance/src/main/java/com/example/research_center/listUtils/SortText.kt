package com.example.research_center.listUtils

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
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

/*
* Customised sort text component,
* allow three states: desc, asc and non-selected
* */
@SuppressLint("UnrememberedMutableState")
@Composable
fun SortText(
    label: String,
    isSelected: Boolean,
    currentSortType: Int,
    sortCol: String,
    onSortChanged: (sortCol: String, sortTypeOrNone: Int) -> Unit
) {

    // Check sort state by checking if this sort
    // text is selected and current sort type
    val sortState = if (!isSelected) {
        0
    } else {
        when (currentSortType) {
            0 -> 1 // desc
            1 -> 2 // asc
            else -> 0
        }
    }

    // Arrow color
    val arrowDownColor =  if (sortState == 1) Color(SINA_BLUE) else Color.Gray

    val arrowUpColor = if (sortState == 2) Color(SINA_BLUE) else Color.Gray

    // Set text size and get the height to dp
    // Current arrow icon is not really good,
    // so still has some padding. If there's a arrow icon with out
    // any padding, then cna remove the dp after +
    val textSize = 12.sp
    val textHeightDp = with(LocalDensity.current) { textSize.toDp() } + 12.dp

    val onClick = {
        val newState = (sortState + 1) % 3
        val newTypeOrNone = when(newState) {
            1 -> 0 // desc
            2 -> 1 // asc
            else -> -1 // none
        }

        val newSortCol = if (newState == 0) "" else sortCol

        onSortChanged(newSortCol, newTypeOrNone)
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { onClick() }
    ) {
        // Text
        Text(
            text = label,
            color = Color.Gray,
            fontSize = 12.sp,
        )

        // Arrow
        Column(
            modifier = Modifier
                .height(textHeightDp)
        ) {
            // arrow up
            Icon(
                painter = painterResource(R.drawable.arrow_triangle_up),
                contentDescription = "arrow triangle up",
                tint = arrowUpColor,
                modifier = Modifier
                    .wrapContentSize()
                    .size(textHeightDp * 0.5f)
            )
            // arrow down
            Icon(
                painter = painterResource(R.drawable.arrow_triangle_down),
                contentDescription = "arrow triangle down",
                tint = arrowDownColor,
                modifier = Modifier
                    .wrapContentSize()
                    .size(textHeightDp * 0.5f)
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(
    name = "SortText Preview",
    showBackground = true
)
@Composable
fun SortTextPreview() {
    SortText(
        label = "研报数量",
        isSelected = true,
        currentSortType = 1,
        sortCol = "num",
        onSortChanged = { _, _ -> }
    )
}