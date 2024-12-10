package com.example.lists.presentation.dog_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.lists.R
import com.example.lists.domain.model.Dog

/*
* This file is to set the elements in each item in the LazyVerticalStaggeredGrid
* There's an image as the element, wrapped by a clickable box.
* */
@Composable
fun DogListItem(
    dog: Dog,
    onItemClick: (Dog) -> Unit
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)
                .clickable { onItemClick(dog) }
        ) {
            DogListImage(dog = dog)
        }
}

// By using AsyncImage, the height of the image can be different.
@Composable
private fun DogListImage(dog: Dog) {
    AsyncImage(
        model = dog.url,
        contentScale = ContentScale.Crop,
        contentDescription = null,
        error = painterResource(R.drawable.no_image),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    )
}