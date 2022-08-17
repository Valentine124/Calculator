package com.valentine.calculator.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.valentine.calculator.internet.Currencies
import kotlinx.coroutines.flow.Flow

@Dao
interface ConverterDao {
    @Query("SELECT * FROM converter")
    suspend fun getCurrency(): List<Currencies>

    @Insert
    suspend fun addCurrency(currencies: Currencies)
}