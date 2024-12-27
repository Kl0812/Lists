package com.example.research_center.presentation.stock_detail

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.research_center.listUtils.CustomTopBar
import com.example.research_center.listUtils.ShowDate

@SuppressLint("NewApi")
@Composable
fun StockDetailScreen(
    navController: NavController,
    viewModel: StockDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    Scaffold(
        containerColor = Color.White,
        topBar = {
            CustomTopBar(
                title = "研报",
                showReturnButton = true,
                onReturnClick = {
                    navController.popBackStack()
                }
            )
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            state.stockDetail?.let { detail ->

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                        .verticalScroll(rememberScrollState())
                ) {

                    Text(
                        text = detail.title,
                        fontSize = 24.sp,
                        fontWeight = Bold,
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "机构：${detail.orgname}",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "研究员：${detail.author}",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    ShowDate(detail.adddate)
                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = detail.reportinfo,
                        fontSize = 20.sp
                    )
                }
            }

            if(state.error.isNotBlank()) {
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

            if(state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        }
    }
}