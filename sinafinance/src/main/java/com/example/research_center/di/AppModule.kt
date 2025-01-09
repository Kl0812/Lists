package com.example.research_center.di

import com.example.research_center.common.Constants
import com.example.research_center.data.remote.ReportCenterApi
import com.example.research_center.data.remote.ReportDetailApi
import com.example.research_center.data.remote.StockService
import com.example.research_center.data.repository.StockServiceImpl
import com.example.research_center.data.repository.ReportCenterRepositoryImpl
import com.example.research_center.data.repository.StockRepositoryImpl
import com.example.research_center.domain.repository.ReportCenterRepository
import com.example.research_center.domain.repository.StockRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.websocket.WebSockets
import retrofit2.Retrofit
import javax.inject.Singleton
import retrofit2.converter.gson.GsonConverterFactory

/*
* Set up a Hilt module for Dependency Injection.
* Able to create fake repository for test purpose
* */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /*
    * For normal api usage
    * */
    @Provides
    @Singleton
    fun provideReportCenterApi(): ReportCenterApi {
        return Retrofit.Builder()
            .baseUrl(Constants.LIST_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ReportCenterApi::class.java)
    }

    @Provides
    @Singleton
    fun provideReportDetailApi(): ReportDetailApi {
        return Retrofit.Builder()
            .baseUrl(Constants.DETAIL_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ReportDetailApi::class.java)
    }

    @Provides
    @Singleton
    fun provideReportCenterRepository(
        listApi: ReportCenterApi,
        detailApi: ReportDetailApi
    ): ReportCenterRepository {
        return ReportCenterRepositoryImpl(listApi, detailApi)
    }

    /*
    * For websocket usage
    * */
    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(CIO) {
            install(WebSockets)
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
        }
    }

    @Provides
    @Singleton
    fun provideStockService(client: HttpClient): StockService {
        return StockServiceImpl(client)
    }

    @Provides
    @Singleton
    fun provideStockRepository(
        stockService: StockService
    ): StockRepository {
        return StockRepositoryImpl(stockService)
    }
}