package com.applications.currencyconverter.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applications.currencyconverter.data.db.entities.CurrencyEntities
import com.applications.currencyconverter.models.CurrencyDTO
import com.applications.currencyconverter.models.LiveCurrencyRateDTO
import com.applications.currencyconverter.utils.NetworkResult
import com.example.currencyapplication.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val callListResponse: LiveData<NetworkResult<CurrencyDTO>>
        get() = repository.remote.currencyListResponse


    val liveCurrencyResponse: LiveData<NetworkResult<LiveCurrencyRateDTO>>
        get() = repository.remote.currencyConvertResponse


    var liveGetLocalData: MutableLiveData<List<CurrencyEntities>> = MutableLiveData()


    fun getCurrencyList() {
        viewModelScope.launch {
            repository.remote.getCurrencyList()
        }
    }

    fun getCurrencyLive(source: String) {
        viewModelScope.launch {
            repository.remote.liveCurrency(source)
        }
    }


    suspend fun saveLocal(list: ArrayList<CurrencyEntities>) {

        viewModelScope.async(Dispatchers.IO) {
            repository.local.deleteData()
        }.await()
        repository.local.insertCurrencyData(list)
    }

    fun getLocal() {

        liveGetLocalData.postValue(repository.local.getCurrencyData())

    }


}