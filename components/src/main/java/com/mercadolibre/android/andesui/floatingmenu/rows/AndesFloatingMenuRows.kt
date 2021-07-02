package com.mercadolibre.android.andesui.floatingmenu.rows

/**
 * Utility class that defines possible [AndesFloatingMenuRows] types. AndesFloatingMenuRows will
 * configure [com.mercadolibre.android.andesui.floatingmenu.AndesFloatingMenu] height.
 * As a bonus it gives you the proper implementation so you don't have to make any mapping.
 *
 * You ask me with, let's say 'Small', and then I'll give you a proper implementation of that size.
 */
sealed class AndesFloatingMenuRows {
    object Small : AndesFloatingMenuRows()
    object Medium : AndesFloatingMenuRows()
    object Max : AndesFloatingMenuRows()

    companion object {
        fun fromString(value: String): AndesFloatingMenuRows {
            return when (value) {
                Small.javaClass.simpleName -> Small
                Max.javaClass.simpleName -> Max
                else -> Medium
            }
        }
    }

    internal val rows get() = getAndesFloatingMenuRows()

    private fun getAndesFloatingMenuRows(): AndesFloatingMenuRowsInterface {
        return when (this) {
            Small -> AndesSmallFloatingMenuRows()
            Medium -> AndesMediumFloatingMenuRows()
            Max -> AndesMaxFloatingMenuRows()
        }
    }
}
