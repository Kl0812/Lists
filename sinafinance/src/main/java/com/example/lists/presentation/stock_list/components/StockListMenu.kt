package com.example.lists.presentation.stock_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lists.R

/*
* This file is to create list menu header
* */
@Composable
fun StockListMenu() {
    var isDropDownExpanded by remember { mutableStateOf(false) }
    var currentFilter by remember { mutableStateOf("全部") }


    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "近三月行业研报情况",
            fontSize = 16.sp
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
                    text = "评级变动：$currentFilter",
                    fontSize = 16.sp,
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

                val itemPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)

                MenuItem(
                    text = "全部",
                    onSelected = {
                        currentFilter = it
                        isDropDownExpanded = false
                                 },
                    itemPadding
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
                        currentFilter = it
                        isDropDownExpanded = false
                                 },
                    itemPadding
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
                        currentFilter = it
                        isDropDownExpanded = false
                                 },
                    itemPadding)

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
                        currentFilter = it
                        isDropDownExpanded = false
                                 },
                    itemPadding)

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
                        currentFilter = it
                        isDropDownExpanded = false
                                 },
                    itemPadding)
            }
        }
    }
}

// Function to handle process when click on the item
@Composable
fun MenuItem(
    text: String,
    onSelected: (String) -> Unit,
    contentPadding: PaddingValues
) {
    DropdownMenuItem(
        text = {
            Text(
                text = text,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        onClick = { onSelected(text) },
        contentPadding = contentPadding
    )
}



@Preview(
    name = "StockListMenu Preview",
    showBackground = true
)
@Composable
fun StockListMenuPreview() {
    StockListMenu()
}
