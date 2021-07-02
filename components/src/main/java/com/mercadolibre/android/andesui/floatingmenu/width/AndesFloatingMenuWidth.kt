package com.mercadolibre.android.andesui.floatingmenu.width

/**
 * Utility class that defines possible [AndesFloatingMenuWidth] types. AndesFloatingMenuWidth will
 * configure [com.mercadolibre.android.andesui.floatingmenu.AndesFloatingMenu] width.
 */
sealed class AndesFloatingMenuWidth {
    /**
     * Will set the width of the trigger.
     */
    object Fixed : AndesFloatingMenuWidth()

    /**
     * Will set a custom width in pixels.
     */
    data class Custom(val width: Int) : AndesFloatingMenuWidth()

    /**
     * Returns [AndesFloatingMenuWidthInterface] according to the "enum" value.
     */
    internal val size get() = getAndesFloatingMenuWidth()

    private fun getAndesFloatingMenuWidth(): AndesFloatingMenuWidthInterface {
        return when (this) {
            is Fixed -> AndesFixedFloatingMenuWidth()
            is Custom -> AndesCustomFloatingMenuWidth(width)
        }
    }
}
