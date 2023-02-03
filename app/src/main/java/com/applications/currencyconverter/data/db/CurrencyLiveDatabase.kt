package com.example.currencyapplication.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.applications.currencyconverter.data.db.entities.CurrencyEntities

@Database(entities = [CurrencyEntities::class], version = 1)
abstract class CurrencyLiveDatabase :RoomDatabase(){
    abstract fun currencyLiveDBDao(): CurrencyLiveDBDao
}