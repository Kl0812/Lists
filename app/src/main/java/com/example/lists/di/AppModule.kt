package com.example.lists.di

import com.example.lists.common.Constants
import com.example.lists.data.remote.DogApi
import com.example.lists.data.repository.DogRepositoryImpl
import com.example.lists.domain.repository.DogRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDogApi(): DogApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DogApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDogRepository(api: DogApi): DogRepository {
        return DogRepositoryImpl(api)
    }
}