package com.example.research_center.presentation.research_center.pages.report_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lists.R

/*
* This file is to create list menu header
* */
@Composable
fun ReportListMenu(
    onMenuSelected: (String) -> Unit,
    currentRating: Int
) {
    var isDropDownExpanded by remember { mutableStateOf(false) }
    val currentMenuText = remember(currentRating) {
        when(currentRating) {
            1 -> "上调"
            3 -> "下调"
            2 -> "维持"
            4 -> "首次"
            else -> "全部"
        }
    }

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(12.dp)
        ) {
            Text(
                text = "近三月行业研报情况",
                fontSize = 15.sp
            )

            Box(
                modifier = Modifier
                    .wrapContentSize(Alignment.TopEnd)
                    .clickable { isDropDownExpanded = true }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "评级变动：$currentMenuText",
                        fontSize = 13.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Image(
                        painter = painterResource(R.drawable.down_arrow),
                        contentDescription = "DropDown Icon",
                        modifier = Modifier.size(16.dp)
                    )
                }

                DropdownMenu(
                    expanded = isDropDownExpanded,
                    onDismissRequest = { isDropDownExpanded = false },
                    modifier = Modifier
                        .wrapContentSize(Alignment.TopEnd)
                        .background(Color.White)
                ) {

                    MenuItem(
                        text = "全部",
                        onSelected = {
                            onMenuSelected(it)
                            isDropDownExpanded = false
                        }
                    )

                    HorizontalDivider(
                        thickness = 0.2.dp,
                        color = Color.Gray,
                        modifier = Modifier
                            .padding(
                                start = 16.dp,
                                end = 16.dp
                            )
                    )

                    MenuItem(
                        text = "上调",
                        onSelected = {
                            onMenuSelected(it)
                            isDropDownExpanded = false
                        }
                    )

                    HorizontalDivider(
                        thickness = 0.2.dp,
                        color = Color.Gray,
                        modifier = Modifier
                            .padding(
                                start = 16.dp,
                                end = 16.dp
                            )
                    )

                    MenuItem(
                        text = "下调",
                        onSelected = {
                            onMenuSelected(it)
                            isDropDownExpanded = false
                        }
                    )

                    HorizontalDivider(
                        thickness = 0.2.dp,
                        color = Color.Gray,
                        modifier = Modifier
                            .padding(
                                start = 16.dp,
                                end = 16.dp
                            )
                    )

                    MenuItem(
                        text = "维持",
                        onSelected = {
                            onMenuSelected(it)
                            isDropDownExpanded = false
                        }
                    )

                    HorizontalDivider(
                        thickness = 0.2.dp,
                        color = Color.Gray,
                        modifier = Modifier
                            .padding(
                                start = 16.dp,
                                end = 16.dp
                            )
                    )

                    MenuItem(
                        text = "首次",
                        onSelected = {
                            onMenuSelected(it)
                            isDropDownExpanded = false
                        }
                    )
                }
            }
        }

        HorizontalDivider(
            thickness = 0.2.dp,
            color = Color.Gray,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

// Function to handle process when click on the item
@Composable
fun MenuItem(
    text: String,
    onSelected: (String) -> Unit
) {
    DropdownMenuItem(
        text = {
            Text(
                text = text,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        onClick = { onSelected(text) }
    )
}
