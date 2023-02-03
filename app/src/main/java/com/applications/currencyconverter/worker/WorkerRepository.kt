package com.applications.currencyconverter.worker

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.applications.currencyconverter.data.api.CurrencyApiInterface
import com.applications.currencyconverter.data.db.entities.CurrencyEntities
import com.applications.currencyconverter.models.LiveCurrencyRateDTO
import com.applications.currencyconverter.utils.CheckNetworkConnectivity
import com.applications.currencyconverter.utils.Constants
import com.applications.currencyconverter.utils.SharedPreferences
import com.example.currencyapplication.data.repository.LocalDataSource
import com.example.currencyapplication.data.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class WorkerRepository @Inject constructor(
    private val apiInterface: CurrencyApiInterface,
    private val application: Application, val repository: LocalDataSource,
) {

    @Inject
    lateinit var sharedPreferences: SharedPreferences


    suspend fun getRemoteData() {

        val baseCurrency :String = sharedPreferences.getSaveValue(Constants.LAST_SOURCE).toString()


        if (CheckNetworkConnectivity.isInternetAvailable(application) && !baseCurrency.isNullOrEmpty()) {
            val data: Response<LiveCurrencyRateDTO> = apiInterface.currencyLiveData(baseCurrency, "", Constants.KEY)
            if (data.code() == 200 && data.isSuccessful && data.body()!=null) {
                val list: ArrayList<CurrencyEntities> = ArrayList()
                data.body()!!.quotes.forEach {
                    list.add(CurrencyEntities(0, it.key, it.value))
                }
                saveLocal(list)

            }
            else if (data.code()==500){

            }
            else if(data.code()==400){

            }

        }
    }


    suspend fun saveLocal(list: ArrayList<CurrencyEntities>) {

        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteData()
        }
        repository.insertCurrencyData(list)
    }


}