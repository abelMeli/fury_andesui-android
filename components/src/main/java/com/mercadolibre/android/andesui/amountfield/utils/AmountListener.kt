package com.mercadolibre.android.andesui.amountfield.utils

/**
 * Internal interface to detect when the text in the component changes. This callback
 * is triggered inside the corresponding formatter.
 */
internal interface AmountListener {
    fun onAmountChanged(isExceeded: Boolean)
}