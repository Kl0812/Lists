package com.example.research_center.domain.model

/*
* 今日开盘价 Today's Opening Price: top
* 昨日收盘价 Yesterday's Closing Price: ycp
* 最近成交价 Recent Transaction Price: rtp
* 最高成交价 Highest Transaction Price: htp
* 最低成交价 Lowest Transaction Price: ltp
* 买入价 Bid Price: bp
* 卖出价 Ask Price: ap
* 成交数量 Transaction Quantity: tq
* 成交金额 Transaction Amount: ta
* */
data class Stock(
    val stockName: String,
    val top: Float,
    val ycp: Float,
    val rtp: Float,
    val htp: Float,
    val ltp: Float,
    val bp: Float,
    val ap: Float,
    val tq: Float,
    val ta: Float
)