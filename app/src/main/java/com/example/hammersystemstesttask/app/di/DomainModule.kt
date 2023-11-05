package com.example.hammersystemstesttask.app.di

import com.example.hammersystemstesttask.data.repository.CachedFoodRepositoryImpl
import com.example.hammersystemstesttask.data.repository.NetworkFoodRepositoryImpl
import com.example.hammersystemstesttask.domain.repository.CachedFoodRepository
import com.example.hammersystemstesttask.domain.repository.NetworkFoodRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DomainModule {

    @Binds
    fun provideNetworkFoodRepository(networkFoodRepositoryImpl: NetworkFoodRepositoryImpl): NetworkFoodRepository

    @Binds
    fun provideCachedFoodRepository(cachedFoodRepositoryImpl: CachedFoodRepositoryImpl): CachedFoodRepository
}