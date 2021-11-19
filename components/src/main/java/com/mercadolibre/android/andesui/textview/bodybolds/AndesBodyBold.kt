package com.mercadolibre.android.andesui.textview.bodybolds

/**
 * Class that represent a single range of characters to be presented
 * with bold style in a [com.mercadolibre.android.andesui.textview.AndesTextView].
 *
 * This class is always used as part of a list, inside a [com.mercadolibre.android.andesui.textview.bodybolds.AndesBodyBolds]
 * object.
 */
data class AndesBodyBold(val startIndex: Int, val endIndex: Int) {
    fun isValidRange(text: CharSequence): Boolean {
        return (startIndex >= 0 &&
                endIndex >= 0 &&
                startIndex <= endIndex &&
                endIndex <= text.length)
    }
}
