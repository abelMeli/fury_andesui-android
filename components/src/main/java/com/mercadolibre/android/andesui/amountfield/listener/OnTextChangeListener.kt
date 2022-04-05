package com.mercadolibre.android.andesui.amountfield.listener

/**
 * Interface used to detect when the value of the component changes. This value should be added
 * to the [com.mercadolibre.android.andesui.amountfield.AndesAmountFieldSimple.onTextChangedListener]
 * property.
 */
interface OnTextChangeListener {
    fun onTextChanged(newText: String?)
}
