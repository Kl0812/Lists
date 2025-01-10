package com.example.research_center.data.repository

import com.example.research_center.common.Resource
import com.example.research_center.data.remote.StockService
import com.example.research_center.data.remote.dto.StockDto
import com.example.research_center.data.remote.dto.toStock
import com.example.research_center.domain.model.Stock
import io.ktor.client.HttpClient
import io.ktor.client.features.websocket.webSocketSession
import io.ktor.client.request.url
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.WebSocketSession
import io.ktor.http.cio.websocket.close
import io.ktor.http.cio.websocket.readText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.isActive

class StockServiceImpl(
    private val client: HttpClient
): StockService {

    private var socket: WebSocketSession? = null

    override suspend fun initSession(stockCode: String): Resource<Unit> {
        return try {
            socket = client.webSocketSession {
                url("${StockService.Endpoints.StockSocket.url}&list=$stockCode")
            }
            if(socket?.isActive == true) {
                Resource.Success(Unit)
            } else Resource.Error("Couldn't establish a connection.")
        } catch(e: Exception) {
            e.printStackTrace()
            Resource.Error(e.localizedMessage ?: "Unknown error")
        }
    }

    override fun observeStock(): Flow<Stock> {
        return try {
            socket?.incoming
                ?.receiveAsFlow()
                ?.filter { it is Frame.Text }
                ?.map {
                    val rawText = (it as? Frame.Text)?.readText() ?: ""
                    val stockDto = StockDto(information = rawText)
                    stockDto.toStock()
                } ?: flow {  }
        } catch(e: Exception) {
            e.printStackTrace()
            flow {  }
        }
    }

    override suspend fun closeSession() {
        socket?.close()
    }

    override suspend fun getStock(): Stock {
        return Stock(
            stockName = "暂无数据",
            top = "暂无数据",
            ycp = "暂无数据",
            rtp = "暂无数据",
            htp = "暂无数据",
            ltp = "暂无数据",
            bp = "暂无数据",
            ap = "暂无数据",
            tq = "暂无数据",
            ta = "暂无数据"
        )
    }
}