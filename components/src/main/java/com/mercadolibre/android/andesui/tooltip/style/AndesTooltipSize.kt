package com.mercadolibre.android.andesui.tooltip.style

import java.util.Locale

/**
 * This enum is for setting different styles for the tooltip.
 *
 * @property DYNAMIC is to adjust the tooltip to its content
 * @property FULL_SIZE is to adjust the tooltip to match the parent width. It must only work with TOP and BOTTOM
 * Location. The default is always TOP even if the user selects LEFT or RIGHT.
 */
enum class AndesTooltipSize {
    DYNAMIC,
    FULL_SIZE;

    companion object {
        fun fromString(value: String) = valueOf(value.toUpperCase(Locale.ROOT))
    }

    internal val type get() = getAndesTooltipSize()

    private fun getAndesTooltipSize(): AndesTooltipSizeInterface {
        return when (this) {
            DYNAMIC -> AndesTooltipSizeDynamic
            FULL_SIZE -> AndesTooltipSizeFullSize
        }
    }
}
