package com.mercadolibre.android.andesui.textfield.links

import android.text.SpannableString

class AndesTextfieldLink(
    val startIndex: Int,
    val endIndex: Int
) {

    fun isValidRange(text: SpannableString) = startIndex >= 0 &&
            endIndex >= 0 &&
            startIndex <= endIndex &&
            endIndex <= text.length
}
