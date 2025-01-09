package com.example.research_center.presentation.stock

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
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
import com.example.research_center.presentation.qs_symbol_list.QsSymbolListViewModel

@Composable
fun StockScreen(
    navController: NavController,
    viewModel: StockViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    val stockCode = viewModel.stockCode ?: "未知代码"

    Scaffold(
        containerColor = Color.White,
        topBar = {
            CustomTopBar(
                title = stockCode,
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
            state.stock?.let { detail ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                        .verticalScroll(rememberScrollState())
                ) {

                    // 股票名称
                    Content("股票名称", detail.stockName)
                    Spacer(modifier = Modifier.height(12.dp))

                    // 今日开盘价
                    Content("今日开盘价", detail.top.toString())
                    Spacer(modifier = Modifier.height(12.dp))

                    // 昨日收盘价
                    Content("昨日收盘价", detail.ycp.toString())
                    Spacer(modifier = Modifier.height(12.dp))

                    // 最近成交价
                    Content("最近成交价", detail.rtp.toString())
                    Spacer(modifier = Modifier.height(12.dp))

                    // 最高成交价
                    Content("最高成交价", detail.htp.toString())
                    Spacer(modifier = Modifier.height(12.dp))

                    // 最低成交价
                    Content("最低成交价", detail.ltp.toString())
                    Spacer(modifier = Modifier.height(12.dp))

                    // 买入价
                    Content("买入价", detail.bp.toString())
                    Spacer(modifier = Modifier.height(12.dp))

                    // 卖出价
                    Content("卖出价", detail.ap.toString())
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    // 成交数量
                    Content("成交数量", detail.tq.toString())
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    // 成交金额
                    Content("成交金额", detail.ta.toString())
                    
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

@Composable
fun Content(
    title: String,
    num: String
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            modifier = Modifier.weight(1f),
        )

        Text(
            text = num,
            fontSize = 16.sp,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End,
            color = Color.Black
        )
    }
}