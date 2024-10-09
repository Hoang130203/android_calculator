package com.example.calculator


import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private lateinit var tvResult: TextView
    private lateinit var btnCE: Button
    private lateinit var btnC: Button
    private lateinit var btnBS: Button
    private lateinit var btnDivide: Button
    private lateinit var btn7: Button
    private lateinit var btn8: Button
    private lateinit var btn9: Button
    private lateinit var btnX: Button
    private lateinit var btn4: Button
    private lateinit var btn5: Button
    private lateinit var btn6: Button
    private lateinit var btn_: Button
    private lateinit var btn1: Button
    private lateinit var btn2: Button
    private lateinit var btn3: Button
    private lateinit var btnadd: Button
    private lateinit var btnAddAndSub: Button
    private lateinit var btn0: Button
    private lateinit var btndot: Button
    private lateinit var btnequal: Button
    private var isCleared = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.constrain_layout)


        tvResult = findViewById(R.id.tvResult)
        btnCE = findViewById(R.id.btnCE)
        btnC = findViewById(R.id.btnC)
        btnBS = findViewById(R.id.btnBS)
        btnDivide = findViewById(R.id.btnDivide)
        btn7 = findViewById(R.id.btn7)
        btn8 = findViewById(R.id.btn8)
        btn9 = findViewById(R.id.btn9)
        btnX = findViewById(R.id.btnX)
        btn4 = findViewById(R.id.btn4)
        btn5 = findViewById(R.id.btn5)
        btn6 = findViewById(R.id.btn6)
        btn_ = findViewById(R.id.btn_)
        btn1 = findViewById(R.id.btn1)
        btn2 = findViewById(R.id.btn2)
        btn3 = findViewById(R.id.btn3)
        btnadd = findViewById(R.id.btnadd)
        btnAddAndSub = findViewById(R.id.btn_add_and_sub)
        btn0 = findViewById(R.id.btn0)
        btndot = findViewById(R.id.btndot)
        btnequal = findViewById(R.id.btnequal)


        btnCE.setOnClickListener { handleButtonClick("CE") }
        btnC.setOnClickListener { handleButtonClick("C") }
        btnBS.setOnClickListener { handleButtonClick("BS") }
        btnDivide.setOnClickListener { handleButtonClick("/") }
        btn7.setOnClickListener { handleButtonClick(7) }
        btn8.setOnClickListener { handleButtonClick(8) }
        btn9.setOnClickListener { handleButtonClick(9) }
        btnX.setOnClickListener { handleButtonClick("X") }
        btn4.setOnClickListener { handleButtonClick(4) }
        btn5.setOnClickListener { handleButtonClick(5) }
        btn6.setOnClickListener { handleButtonClick(6) }
        btn_.setOnClickListener { handleButtonClick("-") }
        btn1.setOnClickListener { handleButtonClick(1) }
        btn2.setOnClickListener { handleButtonClick(2) }
        btn3.setOnClickListener { handleButtonClick(3) }
        btnadd.setOnClickListener { handleButtonClick("+") }
        btnAddAndSub.setOnClickListener { handleButtonClick("+/-") }
        btn0.setOnClickListener { handleButtonClick(0) }
        btndot.setOnClickListener { handleButtonClick(".") }
        btnequal.setOnClickListener { handleButtonClick("=") }
    }

    private fun handleButtonClick(s: String) {
        if(isCleared){
            tvResult.text= "0"
        }
        when (s) {
            "CE", "C" -> {
                tvResult.text = "0"
                isCleared = true
            }
            "BS" -> {
                val currentText = tvResult.text.toString()
                if (currentText.isNotEmpty()) {
                    tvResult.text = currentText.dropLast(1)
                }
                if(tvResult.text.isEmpty())
                {
                    tvResult.text="0"
                    isCleared= true
                }

            }
            ".","+/-"->{isCleared=false}
            "=" -> {
                if(tvResult.text.isNotEmpty()){
                    tvResult.append("\n"+evaluate(tvResult.text))
                }
                isCleared=true

            }
            else -> {
                tvResult.append(s)
                isCleared = false
            }
        }
    }

    private fun handleButtonClick(s: Int) {
        if (isCleared || tvResult.text == "0") {
            tvResult.text = s.toString()
            isCleared = false
        } else {
            tvResult.append(s.toString())
        }
    }

    private fun evaluate(expression: CharSequence): String {
        return try {
            // Chuyển biểu thức thành danh sách các token (số và phép toán)
            val tokens = mutableListOf<String>()
            var number = StringBuilder()

            for (char in expression) {
                if (char.isDigit() || char == '.') {
                    number.append(char)
                } else {
                    if (number.isNotEmpty()) {
                        tokens.add(number.toString())
                        number = StringBuilder()
                    }
                    tokens.add(char.toString())
                }
            }
            if (number.isNotEmpty()) {
                tokens.add(number.toString())
            }

            var i = 0
            while (i < tokens.size) {
                if (tokens[i] == "X" || tokens[i] == "/") {
                    val left = tokens[i - 1].toDouble()
                    val right = tokens[i + 1].toDouble()
                    val result = if (tokens[i] == "X") left * right else left / right

                    tokens[i - 1] = result.toString()
                    tokens.removeAt(i)
                    tokens.removeAt(i)

                    i--
                } else {
                    i++
                }
            }

            var result = tokens[0].toDouble()
            i = 1
            while (i < tokens.size) {
                val operator = tokens[i]
                val right = tokens[i + 1].toDouble()

                result = if (operator == "+") {
                    result + right
                } else {
                    result - right
                }
                i += 2
            }

            if (result == result.toInt().toDouble()) {
                result.toInt().toString()
            } else {
                result.toString()
            }
        } catch (e: Exception) {
            "Error"
        }
    }


    private fun String.isDigitsOnly(): Boolean {
        return this.all { it.isDigit() }
    }
}
