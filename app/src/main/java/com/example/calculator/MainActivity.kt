package com.example.calculator

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.*

class MainActivity : AppCompatActivity() {
private var expression = mutableListOf<String>()
private var res = 0.0
private var canClear = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun numberClick(view: View) {
        checkIfClear()
        val button = view as Button
        val displayLow = textView_display2.text.toString() + button.text.toString()
        textView_display2.text = displayLow
    }

    fun clearClick(view: View) {
        textView_display2.text = ""
        textView_display1.text = ""
        expression = mutableListOf()
    }

    fun basicOperationClick(view: View) {
        checkIfClear()
        if (textView_display2.text.isNotBlank() && !textView_display1.text.contains("^")) {
            expression.add(textView_display2.text.trim{ char -> char == '.'}.toString())
            val operation = view as Button
            expression.add(operation.text.toString())
            var displayHigh = ""
            expression.forEach {
                displayHigh += "$it "
            }
            textView_display1.text = displayHigh
            textView_display2.text = ""
        }
    }

    fun equalsClick(view: View) {
        if (textView_display2.text.isNotBlank() && textView_display1.text.contains("^")) {
            val displayHigh = textView_display1.text.toString() + " ${textView_display2.text} ="
            textView_display1.text = displayHigh
            val list = displayHigh.split(" ") as MutableList<String>
            val displayLow = (list[0].toDouble().pow(list[2].toDouble())).toString()
            textView_display2.text = displayLow
            canClear = true
        }
        if (textView_display2.text.isNotBlank() && !textView_display1.text.contains("=") && textView_display1.text.isNotBlank()) {
            expression.add(textView_display2.text.trim{ char -> char == '.'}.toString())
            var displayHigh = ""
            expression.forEach {
                displayHigh += "$it "
            }
            displayHigh += "="
            textView_display1.text = displayHigh
            res = calculate(expression)
            textView_display2.text = res.toString()
            canClear = true
        }
    }

    fun sinClick(view: View) {
        val value = textView_display2.text.trim{ char -> char == '.'}.toString()
        if (value.isNotBlank()) {
            val displayHigh = "sin($value) = "
            textView_display1.text = displayHigh
            textView_display2.text = sin(value.toDouble()).toString()
            canClear = true
        }
        expression = mutableListOf()
    }

    fun cosClick(view: View) {
        val value = textView_display2.text.trim{ char -> char == '.'}.toString()
        if (value.isNotBlank()) {
            val displayHigh = "cos($value) = "
            textView_display1.text = displayHigh
            textView_display2.text = cos(value.toDouble()).toString()
            canClear = true
        }
        expression = mutableListOf()
    }

    fun tanClick(view: View) {
        val value = textView_display2.text.trim{ char -> char == '.'}.toString()
        if (value.isNotBlank()) {
            val displayHigh = "tan($value) = "
            textView_display1.text = displayHigh
            textView_display2.text = tan(value.toDouble()).toString()
            canClear = true
        }
        expression = mutableListOf()
    }

    fun rootClick(view: View) {
        val value = textView_display2.text.trim{ char -> char == '.'}.toString()
        if (value.isNotBlank()) {
            val displayHigh = "√$value = "
            textView_display1.text = displayHigh
            textView_display2.text = sqrt(value.toDouble()).toString()
            canClear = true
        }
        expression = mutableListOf()
    }

    fun signClick(view: View) {
        if (textView_display2.text.isNotBlank()) {
            textView_display2.text = (textView_display2.text.toString().toDouble() * -1).toString()
        }
    }

    fun powClick(view: View) {
        val value = textView_display2.text.trim{ char -> char == '.'}.toString()
        if (value.isNotBlank()) {
            expression = mutableListOf()
            expression.add(value)
            val displayHigh = "$value ^"
            textView_display1.text = displayHigh
            textView_display2.text = ""
            canClear = false
        }
    }

    fun percentClick(view: View) {
        val value = textView_display2.text.toString().toDoubleOrNull()
        if (value != null) {
            textView_display2.text = (value / 100).toString()
        }
    }

    fun periodClick(view: View) {
        var value = textView_display2.text.toString()
        if(!value.contains(".")) {
            if (value.isNotBlank()) {
                value += "."
            } else {
                value = "0."
            }
            textView_display2.text = value
        }
    }

    fun factorialClick(view: View) {
        val value = textView_display2.text.toString().toIntOrNull()
        if (value != null && value != 0) {
            val displayHigh = "$value!"
            textView_display1.text = displayHigh
            textView_display2.text = factorial(value).toString()
            canClear = true
        }
        expression = mutableListOf()
    }

    fun sendClick(view: View) {
        val intent = Intent(this, ResultScreen::class.java)
        var message = ""
        if (textView_display2.text.isNotBlank() && textView_display1.text.contains("^")) {
            val displayHigh = textView_display1.text.toString() + " ${textView_display2.text} ="
            textView_display1.text = displayHigh
            val list = displayHigh.split(" ") as MutableList<String>
            val displayLow = (list[0].toDouble().pow(list[2].toDouble())).toString()
            textView_display2.text = displayLow
            canClear = true
            message = "$displayHigh $displayLow"
        }
        if (textView_display2.text.isNotBlank() && !textView_display1.text.contains("=") && textView_display1.text.isNotBlank()) {
            expression.add(textView_display2.text.trim{ char -> char == '.'}.toString())
            var displayHigh = ""
            expression.forEach {
                displayHigh += "$it "
            }
            displayHigh += "="
            textView_display1.text = displayHigh
            res = calculate(expression)
            textView_display2.text = res.toString()
            canClear = true
            message = "$displayHigh $res"
        }
        if (message.isNotBlank()) {
            intent.putExtra("expression", message)
            startActivity(intent)
        }
    }

    private fun factorial(i: Int): Int {
        if (i == 1) {
            return i
        }
        return i * factorial(i - 1)
    }

    private fun checkIfClear() {
        if (canClear) {
            clearClick(View(this))
            canClear = false
        }
    }

    private fun calculate(list: MutableList<String>): Double {
        while (list.indexOf("-") != -1) {
            val index = list.indexOf("-")
            list[index] = "+"
            list[index + 1] = (list[index + 1].toDouble() * -1).toString()
        }
        while (list.indexOf("*") != -1 || list.indexOf("/") != -1) {
            val index = mostPrecedent(list.indexOf("*"), list.indexOf("/"))
            if (list[index] == "*") {
                list[index] = (list[index - 1].toDouble() * list[index + 1].toDouble()).toString()
                list.removeAt(index + 1)
                list.removeAt(index - 1)
            } else if (list[index] == "/") {
                list[index] = (list[index - 1].toDouble() / list[index + 1].toDouble()).toString()
                list.removeAt(index + 1)
                list.removeAt(index - 1)
            }
        }
        while (list.indexOf("+") != -1) {
            val index = list.indexOf("+")
            list[index] = (list[index - 1].toDouble() + list[index + 1].toDouble()).toString()
            list.removeAt(index + 1)
            list.removeAt(index - 1)
        }
        return list[0].toDouble()
    }

    private fun mostPrecedent(a: Int, b: Int): Int {
        if (a == -1) {
            return b
        }
        if (b == -1) {
            return a
        }
        if (a < b) {
            return a
        }
        return b
    }

}
