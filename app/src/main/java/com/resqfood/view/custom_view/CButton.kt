package com.resqfood.view.custom_view

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton

class CButton (context: Context, attributeSet: AttributeSet): AppCompatButton(context, attributeSet) {

    init {
        applyFont()
    }

    private fun applyFont() {
        val typeface: Typeface =
            Typeface.createFromAsset(context.assets,"Poppins.ttf")
        setTypeface(typeface)
    }

}