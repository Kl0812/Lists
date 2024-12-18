package com.example.lists.di

import com.example.lists.common.Constants
import com.example.lists.data.remote.StockRankingApi
import com.example.lists.data.repository.StockRepositoryImpl
import com.example.lists.domain.repository.StockRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton
import retrofit2.converter.gson.GsonConverterFactory

/*
* Set up a Hilt module for Dependency Injection.
* */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideStockApi(): StockRankingApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(StockRankingApi::class.java)
    }

    @Provides
    @Singleton
    fun provideStockRepository(api: StockRankingApi): StockRepository {
        return StockRepositoryImpl(api)
    }
}