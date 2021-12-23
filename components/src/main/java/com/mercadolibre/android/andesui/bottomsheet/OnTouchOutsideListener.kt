package com.mercadolibre.android.andesui.bottomsheet

/**
 * Bottom sheet on touch outside listener.
 */
interface OnTouchOutsideListener {

    /**
     * Called when the outside background is tapped.
     *
     * @return return true if you want to dismiss sheet when outside background is tapped, false otherwise.
     */
    fun onTouchOutside(): Boolean = true
}
