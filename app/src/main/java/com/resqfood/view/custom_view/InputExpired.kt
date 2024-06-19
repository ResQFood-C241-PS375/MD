package com.resqfood.view.custom_view

import android.content.Context
import android.graphics.Canvas
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import java.util.Calendar

class InputExpired : AppCompatEditText {
    constructor(context: Context) : super(context) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        hint = "dd/mm/yyyy"
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    private fun init(){
        // Mengatur jenis input untuk angka dan batasan panjang karakter
        inputType = InputType.TYPE_CLASS_DATETIME
        filters = arrayOf<InputFilter>(InputFilter.LengthFilter(10))  // Format dd/mm/yyyy memerlukan maksimal 10 karakter

        addTextChangedListener(object : TextWatcher {
            private var current = ""
            private val ddmmyyyy = "DDMMYYYY"
            private val cal = Calendar.getInstance()

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Tidak digunakan dalam contoh ini
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                if (s.toString() != current) {
                    var clean = s.replace(Regex("[^\\d.]|\\."), "")
                    val cleanC = current.replace(Regex("[^\\d.]|\\."), "")

                    // Ensure that when we have enough digits for day, month, and year, we start formatting.
                    if (clean.length >= 8) {
                        var day = clean.substring(0, 2)
                        var month = clean.substring(2, 4)
                        var year = clean.substring(4, 8)

                        month = if (month.toInt() < 1) "01" else if (month.toInt() > 12) "12" else month
                        cal.set(Calendar.MONTH, month.toInt() - 1)
                        year = if (year.toInt() < 1900) "1900" else if (year.toInt() > 2100) "2100" else year
                        cal.set(Calendar.YEAR, year.toInt())

                        day = if (day.toInt() > cal.getActualMaximum(Calendar.DATE)) cal.getActualMaximum(Calendar.DATE).toString() else day
                        clean = String.format("%s/%s/%s", day, month, year)

                        current = clean
                        setText(current)
                        setSelection(if (current.length < 10) current.length else 10)
                    }
                }

//                if (s.toString() != current) {
//                    var clean = s.replace(Regex("[^\\d.]|\\."), "")
//                    val cleanC = current.replace(Regex("[^\\d.]|\\."), "")
//
//                    if (clean.length < 8) {
//                        clean = clean.padEnd(8, '0')
//                    }
//
//                    var day = clean.substring(0, 2)
//                    var month = clean.substring(2, 4)
//                    var year = clean.substring(4, 8)
//
//                    month = if (month.toInt() < 1) "01" else if (month.toInt() > 12) "12" else month
//                    cal.set(Calendar.MONTH, month.toInt() - 1)
//                    year = if (year.toInt() < 1900) "1900" else if (year.toInt() > 2100) "2100" else year
//                    cal.set(Calendar.YEAR, year.toInt())
//
//                    day = if (day.toInt() > cal.getActualMaximum(Calendar.DATE)) cal.getActualMaximum(Calendar.DATE).toString() else day
//                    clean = String.format("%s/%s/%s", day, month, year)
//
//                    current = clean
//                    setText(current)
//                    setSelection(if (current.length < 10) current.length else 10)
//                }
            }

            override fun afterTextChanged(s: Editable) {
                // Tidak digunakan dalam contoh ini
            }
        })
    }
}