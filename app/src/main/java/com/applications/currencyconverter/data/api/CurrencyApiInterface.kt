package com.applications.currencyconverter.data.api


import com.applications.currencyconverter.models.CurrencyDTO
import com.applications.currencyconverter.models.LiveCurrencyRateDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.Query


interface CurrencyApiInterface {

    @GET("currency_data/list")
    suspend fun getCurrencyList(@Header("apikey") apikey:String ):Response<CurrencyDTO>

    @GET("currency_data/live")
    suspend fun currencyLiveData(
        @Query("source") source: String,
        @Query("currencies") currencies: String,
        @Header("apikey") apikey: String
    ):Response<LiveCurrencyRateDTO>
}