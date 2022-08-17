package com.valentine.calculator

import android.app.Application
import com.valentine.calculator.db.ConverterDb
import com.valentine.calculator.internet.ConverterApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ConverterApplication: Application() {
    private val db: ConverterDb by lazy {
        ConverterDb.getInstance(this)
    }
    val repository: ConverterRepository by lazy {
        ConverterRepository(ConverterApi.converterService, db.converterDao())
    }

    override fun onCreate() {
        super.onCreate()
        CoroutineScope(Dispatchers.IO).launch {
            if (repository.getCurrency().isEmpty()) {
                repository.addCurrency(repository.getCurrencies().currencies)
            }
        }
    }
}