package com.valentine.calculator.internet

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

private const val BASE_URL = "https://api.apilayer.com/currency_data/"
private const val API_KEY = "cSFF9oNbr13XxWcBSAnDJEKnBpOzr0gB"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL).build()

interface ConverterService {
    @Headers("apikey: $API_KEY")
    @GET("list")
    suspend fun currencies() : CurrencyList

    @Headers("apikey: $API_KEY")
    @GET("convert")
    suspend fun convert(@Query("amount") amount: String,
                        @Query("from") from: String,
                        @Query("to") to: String): Convert
}

object ConverterApi {
    val converterService: ConverterService by lazy {
        retrofit.create(ConverterService::class.java)
    }
}