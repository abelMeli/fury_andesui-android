package com.mercadolibre.android.andesui.amountfield.listener

/**
 * Interface used to detect when a paste event occurs over the amountField component. This value should
 * be added to the [com.mercadolibre.android.andesui.amountfield.AndesAmountFieldSimple.onTextPastedListener]
 * property.
 */
interface OnTextPasteListener {
    fun onTextPasted(pastedText: String?)
}
