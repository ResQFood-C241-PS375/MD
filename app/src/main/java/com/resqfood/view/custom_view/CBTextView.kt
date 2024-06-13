package com.resqfood.view.custom_view

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class CBTextView(context: Context, attributeSet: AttributeSet): AppCompatTextView(context, attributeSet) {

    init {
        applyFont()
    }

    private fun applyFont() {
        val typeface:Typeface =
            Typeface.createFromAsset(context.assets,"Poppins-Bold.ttf")
        setTypeface(typeface)
    }
}