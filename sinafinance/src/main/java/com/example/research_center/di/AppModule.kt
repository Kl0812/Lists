package com.example.research_center.di

import com.example.research_center.common.Constants
import com.example.research_center.data.remote.ReportCenterApi
import com.example.research_center.data.repository.ReportCenterRepositoryImpl
import com.example.research_center.domain.repository.ReportCenterRepository
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
    fun provideStockApi(): ReportCenterApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ReportCenterApi::class.java)
    }

    @Provides
    @Singleton
    fun provideStockRepository(api: ReportCenterApi): ReportCenterRepository {
        return ReportCenterRepositoryImpl(api)
    }
}