package com.applications.currencyconverter.worker

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.applications.currencyconverter.data.api.CurrencyApiInterface
import com.applications.currencyconverter.models.CurrencyDTO
import com.applications.currencyconverter.models.LiveCurrencyRateDTO
import com.applications.currencyconverter.utils.*
import com.example.currencyapplication.data.repository.Repository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltWorker
class CurrencyWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    val repository: WorkerRepository,
    ) : Worker(context, params) {


    override fun doWork(): Result {
        try {

            CoroutineScope(Dispatchers.IO).launch {
                repository.getRemoteData()
            }
        } catch (e: Exception) {
            return Result.failure()
        }


        return Result.success()


    }


}