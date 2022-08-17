package com.valentine.calculator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.valentine.calculator.internet.Convert
import com.valentine.calculator.internet.Currencies

class ConverterViewModel(private val repository: ConverterRepository): ViewModel() {

    suspend fun getCurrency(): List<Currencies> {
        return repository.getCurrency()
    }

    suspend fun convert(amount: String, from: String, to: String): Convert {
        return repository.convert(amount, from, to)
    }
}

class ConverterFactory(private val repository: ConverterRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ConverterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ConverterViewModel(repository) as T
        }
        throw IllegalArgumentException("Class not found")
    }
}