package com.mercadolibre.android.andesui.bullet

import android.text.SpannableStringBuilder

class AndesBulletSpan(val startIndex: Int, val endIndex: Int) {
    fun isValidRange(text: SpannableStringBuilder): Boolean {
        return (startIndex >= 0 &&
                endIndex >= 0 &&
                startIndex <= endIndex &&
                endIndex <= text.length)
    }
}
