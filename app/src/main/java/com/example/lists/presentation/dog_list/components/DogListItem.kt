package com.example.lists.presentation.dog_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.lists.R
import com.example.lists.domain.model.Dog

@Composable
fun DogListItem(
    dog: Dog,
    onItemClick: (Dog) -> Unit
    ) {
        Card(
            modifier = Modifier
                .wrapContentSize()
                .padding(10.dp)
                .clickable { onItemClick(dog) },
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFf7cd60),
            ),
            shape = RoundedCornerShape(corner = CornerSize(16.dp))
        ){
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .wrapContentSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DogImage(dog = dog)
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = dog.breedName ?: "Loading...",
                    modifier = Modifier
                        .fillMaxWidth()
                        .basicMarquee(),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )

            }
        }
}

@Composable
private fun DogImage(dog: Dog) {
    val painter = rememberAsyncImagePainter(ImageRequest.Builder(LocalContext.current).data(
        data = dog.url
    ).apply(block = fun ImageRequest.Builder.() {
        crossfade(true)
    }).build()
    )

    Image(
        painter = painter,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(16.dp))
    )

}