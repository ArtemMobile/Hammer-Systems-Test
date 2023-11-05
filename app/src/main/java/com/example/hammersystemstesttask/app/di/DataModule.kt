package com.example.hammersystemstesttask.app.di

import android.content.Context
import androidx.room.Room
import com.example.hammersystemstesttask.data.source.local.FoodDatabase
import com.example.hammersystemstesttask.data.source.remote.ApiConstants
import com.example.hammersystemstesttask.data.source.remote.FoodApiService
import com.example.hammersystemstesttask.data.source.remote.LoggingInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideApi(retrofitBuilder: Retrofit.Builder): FoodApiService {
        return retrofitBuilder.build().create(FoodApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
    }

    @Provides
    @Singleton
    fun provideOkHttp(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(loggingInterceptor: LoggingInterceptor): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor(loggingInterceptor)
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Provides
    @Singleton
    fun provideFoodDatabase(@ApplicationContext context: Context): FoodDatabase {
        return Room.databaseBuilder(
            context,
            FoodDatabase::class.java,
            FoodDatabase.DATABASE_NAME
        ).build()
    }
}