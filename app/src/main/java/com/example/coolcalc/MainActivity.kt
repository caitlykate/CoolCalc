package com.example.coolcalc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.Exception


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_0.setOnClickListener {setTextFields("0")}
        btn_1.setOnClickListener {setTextFields("1")}
        btn_2.setOnClickListener {setTextFields("2")}
        btn_3.setOnClickListener {setTextFields("3")}
        btn_4.setOnClickListener {setTextFields("4")}
        btn_5.setOnClickListener {setTextFields("5")}
        btn_6.setOnClickListener {setTextFields("6")}
        btn_7.setOnClickListener {setTextFields("7")}
        btn_8.setOnClickListener {setTextFields("8")}
        btn_9.setOnClickListener {setTextFields("9")}
        btn_min.setOnClickListener {
            if (math_operation.text.isNotEmpty())
                setTextFields("-")
        }
        btn_plus.setOnClickListener {
            if (math_operation.text.isNotEmpty() && math_operation.text.last() != '(')
                setTextFields("+")
        }
        btn_div.setOnClickListener {
            if (math_operation.text.isNotEmpty() && math_operation.text.last() != '(')
                setTextFields("/")
        }
        btn_mult.setOnClickListener {
            if (math_operation.text.isNotEmpty() && math_operation.text.last() != '(')
                setTextFields("*")
            }
        btn_del_all.setOnClickListener {
            math_operation.text = ""
            result_text.text = ""
        }
        btn_del.setOnClickListener {
            if (math_operation.text.isNotEmpty()) {
                math_operation.text = math_operation.text.substring(0, math_operation.text.length - 1)
                result_text.text = ""
            }
        }
        btn_brackets.setOnClickListener{
            if (math_operation.text.isEmpty() || math_operation.text.last() == '(' || listOf('-','+','/','*').contains(math_operation.text.last())) setTextFields("(")
            else if (math_operation.text.last() == ')' || math_operation.text.last().isDigit()) {
                    if (math_operation.text.count { it == '('} > 0 && math_operation.text.count { it == '('} > math_operation.text.count { it == ')'}) setTextFields(")")
                    else setTextFields("(")
            }
        btn_dot.setOnClickListener {
            if (math_operation.text.isNotEmpty() && math_operation.text.last().isDigit())
                setTextFields(".")
        }

        btn_prc.setOnClickListener {
             if (math_operation.text.isNotEmpty() && math_operation.text.last().isDigit())
                 setTextFields("%")
        }

        btn_res.setOnClickListener {
            try {
                val ex = ExpressionBuilder(math_operation.text.toString()).build()
                val result = ex.evaluate()
                Log.d("Error", "Message: $ex , $result")
                val longResult = result.toLong()
                result_text.setTextColor(ContextCompat.getColor(this,R.color.graphite))

                    if (result == longResult.toDouble())
                        result_text.text = longResult.toString()
                    else result_text.text = result.toString()


            } catch (e:Exception) {
                Log.d("Error", "Message: ${e.message}")
                result_text.setTextColor(ContextCompat.getColor(this,R.color.red))
                result_text.text = "ERROR"
            }
        }

        }
    }

    private fun setTextFields(str: String){
        if (result_text.text != "") {
            math_operation.text = result_text.text
            result_text.text = ""
        }
        if (listOf("-","+","/","*").contains(str) && math_operation.text.isNotEmpty() &&  listOf('-','+','/','*').contains(math_operation.text.last()) )
            math_operation.text = math_operation.text.substring(0, math_operation.text.length - 1)
        math_operation.append(str)

    }
}