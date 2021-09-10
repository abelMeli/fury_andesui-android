package com.mercadolibre.android.andesui.tooltip.style

import java.util.Locale

enum class AndesTooltipStyle {
    LIGHT,
    DARK,
    HIGHLIGHT;

    companion object {
        fun fromString(value: String): AndesTooltipStyle = valueOf(value.toUpperCase(Locale.ROOT))
    }

    internal val type get() = getAndesTooltipStyle()

    private fun getAndesTooltipStyle(): AndesTooltipStyleInterface {
        return when (this) {
            LIGHT -> AndesTooltipLightStyle
            HIGHLIGHT -> AndesTooltipHighlightStyle
            DARK -> AndesTooltipDarkStyle
        }
    }
}
