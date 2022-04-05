package com.mercadolibre.android.andesui.amountfield.utils

/**
 * Internal interface needed to be able to trigger the component remeasuring and resizing
 * from the formatters
 */
internal interface ResizingListener {
    fun resizeComponentIfNeeded()
}
