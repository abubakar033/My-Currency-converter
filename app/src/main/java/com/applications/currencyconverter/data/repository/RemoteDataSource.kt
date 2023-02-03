package com.example.currencyapplication.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.applications.currencyconverter.data.api.CurrencyApiInterface
import com.applications.currencyconverter.models.CurrencyDTO
import com.applications.currencyconverter.models.LiveCurrencyRateDTO
import com.applications.currencyconverter.utils.CheckNetworkConnectivity
import com.applications.currencyconverter.utils.Constants
import com.applications.currencyconverter.utils.Helper
import com.applications.currencyconverter.utils.NetworkResult

import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiInterface: CurrencyApiInterface,
    private val application: Application) {

    // get country  list
    private val _currencyListResponse=MutableLiveData<NetworkResult<CurrencyDTO>>()
    val currencyListResponse:LiveData<NetworkResult<CurrencyDTO>>
    get() = _currencyListResponse

    // currency list
    private val _currencyConvertResponse= MutableLiveData<NetworkResult<LiveCurrencyRateDTO>>()
    val currencyConvertResponse:LiveData<NetworkResult<LiveCurrencyRateDTO>>
    get() = _currencyConvertResponse


    suspend fun getCurrencyList(){
        try{
            if(CheckNetworkConnectivity.isInternetAvailable(application)){
                _currencyListResponse.postValue(NetworkResult.Loading())
                val response=apiInterface.getCurrencyList(Constants.KEY)
                Helper.handleGenericResponse(response,_currencyListResponse)
            }
            else{
                _currencyListResponse.postValue(NetworkResult.CheckInternet())
            }
        }
        catch (e:Exception){
            _currencyListResponse.postValue(NetworkResult.Error(e.message.toString()))
        }
    }

    suspend fun liveCurrency(source:String){
        try{
            if(CheckNetworkConnectivity.isInternetAvailable(application))
            {
                _currencyConvertResponse.postValue(NetworkResult.Loading())
                val response=apiInterface.currencyLiveData(source,"",Constants.KEY)
                Helper.handleGenericResponse(response,_currencyConvertResponse)
            }
            else{
                _currencyConvertResponse.postValue(NetworkResult.CheckInternet())
            }
        }
        catch (e:Exception){
            _currencyConvertResponse.postValue(NetworkResult.Error(e.message.toString()))
        }
    }
}