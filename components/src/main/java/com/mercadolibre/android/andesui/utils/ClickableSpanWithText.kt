package com.mercadolibre.android.andesui.utils

import android.text.style.ClickableSpan

/**
 * This class is essentially similar to [ClickableSpan], but with the aggregate
 * of a [text] value needed to keep inside the instance the string for the link
 * which will be created.
 */
internal abstract class ClickableSpanWithText(private val text: String?) : ClickableSpan() {
    internal fun getText() = text
}
