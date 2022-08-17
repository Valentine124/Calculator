package com.valentine.calculator

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.valentine.calculator.databinding.ActivityConverterBinding
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.reflect.full.declaredMemberProperties

class ConverterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConverterBinding
    private val model: ConverterViewModel by viewModels {
        ConverterFactory((application as ConverterApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConverterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.apply {
            title = "Converter"
            setNavigationOnClickListener {
                this@ConverterActivity.onBackPressed()
            }
        }

        convertCurrency()
    }

    private fun convertCurrency() {
        CoroutineScope(Dispatchers.IO).launch {
            val currencyMap = mutableMapOf<String, String>()
            try {
                val result = model.getCurrency()[0]
                val list = mutableListOf<String>()
                val currencies = result.javaClass.kotlin.declaredMemberProperties
                for (currency in currencies) {
                    if (currency.name != "id") {
                        with(currency) {
                            list += "${get(result)}"
                            currencyMap["${get(result)}"] = currency.name
                        }
                    } else {
                        continue
                    }
                }
                val arrayAdapter = ArrayAdapter(this@ConverterActivity, R.layout.dropdown_item, list)
                this@ConverterActivity.runOnUiThread {
                    binding.fromCurrency.setAdapter(arrayAdapter)
                    binding.toCurrency.setAdapter(arrayAdapter)
                }

            }catch (e: Exception){
                this@ConverterActivity.runOnUiThread {
                    Toast.makeText(this@ConverterActivity,
                        "An error occurred", Toast.LENGTH_LONG).show()
                }
            }

            //Handle button click
            binding.btnConvert.setOnClickListener {
                it.hideKeyboard()
                val amount = binding.amount.text.toString()
                val from = currencyMap[binding.fromCurrency.text.toString()]
                val to = currencyMap[binding.toCurrency.text.toString()]

                //Do conversion
                if (from != null && to != null) {
                    val coroutineException = CoroutineExceptionHandler {_, _ ->
                        this@ConverterActivity.runOnUiThread {
                            Toast.makeText(this@ConverterActivity,
                                "Check the internet connection and try again", Toast.LENGTH_LONG).show()
                        }
                    }
                    try {
                        CoroutineScope(Dispatchers.IO + coroutineException).launch {
                            val convertResult = model.convert(amount, from, to).result
                            this@ConverterActivity.runOnUiThread {
                                binding.result.text = convertResult.toString()
                            }
                        }

                    } catch (e: Exception) {
                        this@ConverterActivity.runOnUiThread {
                            Toast.makeText(this@ConverterActivity,
                                "Check the internet connection and try again", Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    this@ConverterActivity.runOnUiThread {
                        Toast.makeText(this@ConverterActivity,
                            "Input a valid currency and amount", Toast.LENGTH_LONG).show()
                    }
                }
            }


        }
    }

    private fun View.hideKeyboard() {
        val imm = this@ConverterActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}