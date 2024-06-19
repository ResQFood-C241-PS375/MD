package com.resqfood.view.custom_view

import android.content.Context
import android.graphics.Canvas
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import com.resqfood.R

class InputPrice : AppCompatEditText {
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
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    private fun init() {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().length < 2) {
                    setError(context.getString(R.string.error_text), null)
                } else {
                    error = null
                }
                if (!s.matches(Regex("^[0-9]+$"))) {
                    setError(context.getString(R.string.error_price), null)
                } else {
                    // Hanya menghilangkan error jika sudah memenuhi kedua kondisi
                    if (s.length >= 2) error = null
                }
            }
            override fun afterTextChanged(s: Editable) {

            }
        })
    }
}