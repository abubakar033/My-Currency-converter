package com.example.currencyapplication.data.db


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.applications.currencyconverter.data.db.entities.CurrencyEntities

@Dao
interface CurrencyLiveDBDao {

    @Insert(onConflict = OnConflictStrategy.NONE)
    suspend fun insertCurrency(sellEntity: List<CurrencyEntities>)

    @Query("SELECT * FROM CurrencyLive")
    fun getCurrencyList(): List<CurrencyEntities>

    @Query("DELETE FROM CurrencyLive")
    fun deleteAllRecord()

}