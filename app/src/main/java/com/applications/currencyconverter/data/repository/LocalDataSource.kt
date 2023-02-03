package com.example.currencyapplication.data.repository

import com.applications.currencyconverter.data.db.entities.CurrencyEntities
import com.example.currencyapplication.data.db.CurrencyLiveDBDao
import javax.inject.Inject


class LocalDataSource @Inject constructor(private val  currencyLiveDBDao: CurrencyLiveDBDao) {
    suspend fun insertCurrencyData(list: List<CurrencyEntities>){
        currencyLiveDBDao.insertCurrency(list)

    }
      fun getCurrencyData():List<CurrencyEntities> {
         return currencyLiveDBDao.getCurrencyList()
     }

   suspend  fun deleteData(){
        currencyLiveDBDao.deleteAllRecord()
    }
}