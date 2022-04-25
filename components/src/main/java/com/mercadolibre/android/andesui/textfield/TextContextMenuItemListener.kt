package com.mercadolibre.android.andesui.textfield

/**
 * Interface for listen the actions of copy, cut and paste.
 */
interface TextContextMenuItemListener {
    fun onCut(): Boolean = false
    fun onPaste(): Boolean = false
    fun onCopy(): Boolean = false
}
