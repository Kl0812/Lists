package com.example.research_center.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.research_center.presentation.brokerage_ranking.BrokerageRanking
import com.example.research_center.presentation.industry_ranking.IndustryRanking
import com.example.research_center.presentation.stock_ranking.StockRanking
import kotlinx.coroutines.launch

@SuppressLint("NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResearchCenterScreen(
    navController: NavController
) {

    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 3 })
    val scope = rememberCoroutineScope()

    // Main container for the whole screen
    Scaffold(
        containerColor = Color.White,
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "研报中心",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(Color.White)
                    // navigationIcon = null // TODO: Currently no upper layer to return
                )
                HorizontalDivider(
                    thickness = 0.2.dp,
                    color = Color.Gray,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    ) { innerPadding ->

        val currentPage = pagerState.currentPage

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Three text button to switch page
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // 行业排行page
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    TextButton(
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(0)
                            }
                        }) {
                        Text("行业排行")
                    }

                    Box(
                        modifier = Modifier
                            .height(2.dp)
                            .width(30.dp)
                            .background(
                                if (currentPage == 0) Color.Blue else Color.Transparent
                            )
                    )
                }

                // 个股排行page
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    TextButton(
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(1)
                            }
                        }) {
                        Text("个股排行")
                    }

                    Box(
                        modifier = Modifier
                            .height(2.dp)
                            .width(30.dp)
                            .background(
                                if (currentPage == 1) Color.Blue else Color.Transparent
                            )
                    )
                }

                // 券商排行page
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    TextButton(
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(2)
                            }
                        }) {
                        Text("券商排行")
                    }

                    Box(
                        modifier = Modifier
                            .height(2.dp)
                            .width(30.dp)
                            .background(
                                if (currentPage == 2) Color.Blue else Color.Transparent
                            )
                    )
                }
            }

            HorizontalDivider(
                thickness = 0.2.dp,
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
            )

            // Main content area, able to swipe to switch
            HorizontalPager(
                state = pagerState
            ) { page ->
                when (page) {
                    0 -> IndustryRanking()
                    1 -> StockRanking(navController = navController)
                    2 -> BrokerageRanking()
                }
            }
        }
    }
}