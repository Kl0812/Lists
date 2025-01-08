package com.example.research_center.di

import com.example.research_center.common.Constants
import com.example.research_center.data.remote.ReportCenterApi
import com.example.research_center.data.remote.ReportDetailApi
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
* Able to create fake repository for test purpose
* */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

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
}