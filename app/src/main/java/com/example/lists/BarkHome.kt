package com.example.lists

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.layout.LazyLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import com.example.lists.data.DataProvider

@Composable
fun BarkHomeContent() {
    val puppies = remember {DataProvider.puppyList}

    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(puppies) { puppy ->
            if (puppy.sex == "Female") {
                FemalePuppyListItem(puppy = puppy)
            } else {
                MalePuppyListItem(puppy = puppy)
            }
        }
    }
}