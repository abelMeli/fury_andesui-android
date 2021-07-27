package com.mercadolibre.android.andesui.bottomsheet

interface BottomSheetListener {
    /**
     * Called when the bottomSheet is collapsed
     */
    fun onCollapsed()

    /**
     * Called when the bottomSheet is expanded
     */
    fun onExpanded()

    /**
     * Called when the bottomSheet is half expanded
     */
    fun onHalfExpanded() {
        /* default implementation */
    }

    /**
     * Called when the outside background is tapped
     */
    fun onTouchOutside() {
        /* default implementation */
    }
}
