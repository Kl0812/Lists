package com.example.lists.presentation.dog_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.lists.R
import com.example.lists.domain.model.DogDetail

/*
* This file is to set the detail information of the Dog Detail Screen
* */
@Composable
fun DogDetailScreen(
    viewModel: DogDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFf7cd60))
        .statusBarsPadding()
        .navigationBarsPadding()
    ) {
        state.dog?.let { dog ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                DogDetailImage(dog = dog)
                Spacer(modifier = Modifier.height(16.dp))
                LineText(title = "Dog Breed: ", info = dog.dogBreed)
                LineText(title = "Dog ID: ", info = dog.dogId)
                LineText(title = "Origin: ", info = dog.dogOrigin)
                LineText(title = "Description: ", info = dog.description)
                LineText(title = "Adaptability: ", info = dog.adaptability.toString())
                LineText(title = "Affection Level: ", info = dog.affectionLevel.toString())
                LineText(title = "Child Friendly Level: ", info = dog.childFriendly.toString())
                LineText(title = "Stranger Friendly Level: ", info = dog.strangerFriendly.toString())
                LineText(title = "Social Needs Level: ", info = dog.socialNeeds.toString())
                LineText(title = "Energy Level: ", info = dog.energyLevel.toString())
            }
        }

        if (state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }

}

@Composable
private fun DogDetailImage(dog: DogDetail) {
    AsyncImage(
        model = dog.dogUrl,
        contentScale = ContentScale.Crop,
        contentDescription = null,
        error = painterResource(R.drawable.no_image),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    )

}

@Composable
fun LineText(title: String, info: String) {
    Row(modifier = Modifier
        .fillMaxWidth()
    ) {
        Text(
            text = title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = info,
            fontSize = 24.sp
        )
    }
    Spacer(modifier = Modifier.height(24.dp))
}