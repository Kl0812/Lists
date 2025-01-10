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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.research_center.listUtils.CustomTopBar
import com.example.research_center.presentation.stock.components.StockItem

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
                        .verticalScroll(rememberScrollState())
                ) {

                    // 股票名称
                    StockItem(
                        title = "股票名称",
                        value = detail.stockName
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    // 今日开盘价
                    StockItem(
                        title = "今日开盘价",
                        value = detail.top,
                        ycp = detail.ycp
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    // 最近成交价
                    StockItem(
                        title = "最近成交价",
                        value = detail.rtp,
                        ycp = detail.ycp
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    // 最高成交价
                    StockItem(
                        title = "最高成交价",
                        value = detail.htp,
                        ycp = detail.ycp
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    // 最低成交价
                    StockItem(
                        title = "最低成交价",
                        value = detail.ltp,
                        ycp = detail.ycp
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    // 昨日收盘价
                    StockItem(
                        title = "昨日收盘价",
                        value = detail.ycp
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    // 买入价
                    StockItem(
                        title = "买入价",
                        value = detail.bp
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    // 卖出价
                    StockItem(
                        title = "卖出价",
                        value = detail.ap
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    // 成交数量
                    StockItem(
                        title = "成交数量",
                        value = detail.tq
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    // 成交金额
                    StockItem(
                        title = "成交金额",
                        value = detail.ta
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