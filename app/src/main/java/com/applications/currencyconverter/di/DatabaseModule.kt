package com.example.currencyapplication.di

import android.app.Application
import androidx.room.Room
import com.example.currencyapplication.data.db.CurrencyLiveDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideCurrencyDB(application: Application): CurrencyLiveDatabase {
        return Room.databaseBuilder(application,CurrencyLiveDatabase::class.java,"CurrencyDB").build()
    }

    @Singleton
    @Provides
    fun provideCurrencyDao(currencyDatabase: CurrencyLiveDatabase)=currencyDatabase.currencyLiveDBDao()
}