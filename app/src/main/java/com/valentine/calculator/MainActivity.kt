package com.valentine.calculator

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.valentine.calculator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var model: CalcViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(binding.toolbar2)
        binding.toolbar2.apply {
            title = "Calculator"
        }

        model = ViewModelProvider(this)[CalcViewModel::class.java]

        binding.screenInput.text = model.res
        binding.screenResult.text = model.result

        binding.btnReset.setOnClickListener {
            model.input = ""
            model.result = ""
            model.res = ""

            binding.screenInput.text = model.input
            binding.screenResult.text = model.result
        }

        binding.btnBracketOpen.setOnClickListener {
            model.putInput("(")
            binding.screenInput.append(model.input)
        }

        binding.btnBracketClose.setOnClickListener {
            model.putInput(")")
            binding.screenInput.append(model.input)
        }

        binding.btnDivide.setOnClickListener {
            model.putInput("/")
            appendOperators(model.input)
        }

        binding.btnSeven.setOnClickListener {
            model.putInput("7")
            binding.screenInput.append(model.input)
        }

        binding.btnEight.setOnClickListener {
            model.putInput("8")
            binding.screenInput.append(model.input)
        }

        binding.btnNine.setOnClickListener {
            model.putInput("9")
            binding.screenInput.append(model.input)
        }

        binding.btnMinus.setOnClickListener {
            model.putInput("-")
            appendOperators(model.input)
        }

        binding.btnFour.setOnClickListener {
            model.putInput("4")
            binding.screenInput.append(model.input)
        }

        binding.btnFive.setOnClickListener {
            model.putInput("5")
            binding.screenInput.append(model.input)
        }

        binding.btnSix.setOnClickListener {
            model.putInput("6")
            binding.screenInput.append(model.input)
        }

        binding.btnPlus.setOnClickListener {
            model.putInput("+")
            appendOperators(model.input)
        }

        binding.btnOne.setOnClickListener {
            model.putInput("1")
            binding.screenInput.append(model.input)
        }

        binding.btnTwo.setOnClickListener {
            model.putInput("2")
            binding.screenInput.append(model.input)
        }

        binding.btnThree.setOnClickListener {
            model.putInput("3")
            binding.screenInput.append(model.input)
        }

        binding.btnTimes.setOnClickListener {
            model.putInput("*")
            appendOperators(model.input)
        }

        binding.btnDot.setOnClickListener {
            model.putInput(".")
            binding.screenInput.append(model.input)
        }

        binding.btnZero.setOnClickListener {
            model.putInput("0")
            binding.screenInput.append(model.input)
        }

        binding.btnDelete.setOnClickListener {
            val string = binding.screenInput.text.toString()
            val string2 = model.res
            if (string.isNotEmpty()) {
                model.res = string2.substring(0, string2.length-1)
                binding.screenInput.text = string.substring(0, string.length-1)
            }
            model.result = ""
            binding.screenResult.text = ""
        }

        binding.btnEquals.setOnClickListener {
            try {
                val expression = ExpressionBuilder(binding.screenInput.text.toString()).build()
                val result = expression.evaluate()
                val longResult = expression.evaluate().toLong()

                if (result == longResult.toDouble()) {
                    model.result = longResult.toString()
                } else {
                    model.result = result.toString()
                }

                binding.screenResult.text = model.result

            } catch (e: Exception) {
                binding.screenResult.text = getString(R.string.error_message)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.item_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.item_converter -> {
                startActivity(Intent(this, ConverterActivity::class.java))
                true
            }
            else -> {super.onOptionsItemSelected(item)}
        }
    }

    private fun appendOperators(string: String) {
        if (binding.screenResult.text.isNotEmpty()) {
            binding.screenInput.text = ""
            binding.screenInput.append(binding.screenResult.text)
            binding.screenResult.text = ""
            binding.screenInput.append(string)
        } else {
            binding.screenInput.append(string)
        }
    }
}