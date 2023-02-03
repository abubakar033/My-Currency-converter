package com.applications.currencyconverter

import android.app.Application
import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.*
import com.applications.currencyconverter.worker.CurrencyWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@HiltAndroidApp

class MyApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var worker: HiltWorkerFactory
    override fun onCreate() {
        super.onCreate()
        setUpWorker()
    }

    private fun setUpWorker() {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<CurrencyWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(applicationContext).enqueue(workRequest)


    }

    override fun getWorkManagerConfiguration(): Configuration = Configuration.Builder()
        .setWorkerFactory(worker)
        .build()

}