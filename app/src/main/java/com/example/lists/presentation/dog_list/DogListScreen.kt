package com.example.lists.presentation.dog_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import com.example.lists.presentation.Screen
import com.example.lists.presentation.dog_list.components.DogListItem
import com.example.lists.presentation.dog_list.components.PullToRefreshLazyVerticalStaggeredGrid


/*
* This file is used to display the Dog List Screen of the app.
* It is also the entry screen of the app currently.
* */
@Composable
fun DogListScreen(
    navController: NavController,
    viewModel: DogListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    var isRefreshing by remember {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()

    // Main container for the whole screen
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFf7cd60))
    ) {
        // If load for the first time or refresh the page, show loading animation
        if (state.isLoading && state.dogs.isEmpty()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            Column(modifier = Modifier
                .fillMaxSize()
            ) {
                //Header
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(Color(0xFFf7cd60))
                    //Priority: Top layer
                    .zIndex(2f)
                ) {
                    Text(
                        text = "Dog Images",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
                // Main Content
                PullToRefreshLazyVerticalStaggeredGrid(
                    items = state.dogs,
                    content = { dog ->
                        DogListItem(
                            dog = dog,
                            // Used for navigating to detail screen when click the image
                            onItemClick = {
                                navController.navigate(
                                    Screen.DogDetailScreen.route + "/${dog.id}"
                                )
                            }
                        )
                    },
                    isRefreshing = isRefreshing,
                    // Main logic refresh function
                    onRefresh = {
                        scope.launch {
                            isRefreshing = true
                            delay(3000L) // TODO: Simulated API call, add logic here
                            isRefreshing = false
                        }
                    }
                )
            }
        }

        // Actions if app state is error
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

    }
}

// item {
//     if (state.isLoadingMore) {
//         // Show loading indicator for load-more scenario
//         CircularProgressIndicator(
//             modifier = Modifier
//                 .fillMaxWidth()
//                 .padding(16.dp)
//                 .wrapContentSize(Alignment.Center)
//         )
//     } else if (/* condition to show pull-up hint */) {
//         Text(
//             text = "Pull up to load more...",
//             textAlign = TextAlign.Center,
//             modifier = Modifier
//                 .fillMaxWidth()
//                 .padding(16.dp)
//         )
//     }
// }

/*// Pull up and load more
LaunchedEffect(listState) {
    snapshotFlow {
        listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
    }.collect { lastVisibleItemIndex ->
        // If the user has scrolled to the last item, we can load more data automatically
        // or show a prompt. For manual "pull up" gestures, you'd need a custom approach,
        // but infinite scroll is often handled this way.
        if (lastVisibleItemIndex == state.dogs.lastIndex && !state.isLoadingMore) {
            viewModel.getDogs()
        }
    }
}*/