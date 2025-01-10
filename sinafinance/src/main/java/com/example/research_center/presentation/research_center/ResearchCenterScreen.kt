package com.example.research_center.presentation.research_center

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.research_center.listUtils.CustomTopBar
import com.example.research_center.presentation.hy_list.HyListScreen
import com.example.research_center.presentation.qs_list.QsListScreen
import com.example.research_center.presentation.report_list.ReportListScreen
import com.example.research_center.presentation.research_center.components.SubPage

@SuppressLint("NewApi")
@Composable
fun ResearchCenterScreen(
    navController: NavController // Currently no upper layer to return
) {

    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 3 })
    val scope = rememberCoroutineScope()

    // Main container for the whole screen
    Scaffold(
        containerColor = Color.White,
        topBar = {
            CustomTopBar(
                title = "研报中心"
            )
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
                SubPage(
                    text = "行业排行",
                    currentPage = currentPage,
                    myPage = 0,
                    scope = scope,
                    pagerState = pagerState
                )

                // 个股排行page
                SubPage(
                    text = "个股排行",
                    currentPage = currentPage,
                    myPage = 1,
                    scope = scope,
                    pagerState = pagerState
                )

                // 券商排行page
                SubPage(
                    text = "券商排行",
                    currentPage = currentPage,
                    myPage = 2,
                    scope = scope,
                    pagerState = pagerState
                )
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
                    0 -> HyListScreen(navController)
                    1 -> ReportListScreen(navController)
                    2 -> QsListScreen(navController)
                }
            }
        }
    }
}
