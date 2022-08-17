package com.valentine.calculator

import com.valentine.calculator.db.ConverterDao
import com.valentine.calculator.internet.Convert
import com.valentine.calculator.internet.ConverterService
import com.valentine.calculator.internet.Currencies
import com.valentine.calculator.internet.CurrencyList

class ConverterRepository(private val service: ConverterService, private val converterDao: ConverterDao) {
    suspend fun getCurrencies(): CurrencyList {
        return service.currencies()
    }

    suspend fun convert(amount: String, from: String, to: String): Convert {
        return service.convert(amount, from, to)
    }

    //DB
    suspend fun getCurrency(): List<Currencies> {
        return converterDao.getCurrency()
    }

    suspend fun addCurrency(currencies: Currencies) {
        converterDao.addCurrency(currencies)
    }
}