package com.mercadolibre.android.andesui.checkbox.align

import java.util.Locale

enum class AndesCheckboxAlign {
    LEFT,
    RIGHT;

    companion object {
        fun fromString(value: String): AndesCheckboxAlign = valueOf(value.toUpperCase(Locale.ROOT))
    }

    internal val type get() = getAndesCheckboxAlign()

    private fun getAndesCheckboxAlign(): AndesCheckboxAlignInterface {
        return when (this) {
            LEFT -> AndesCheckboxLeft
            RIGHT -> AndesCheckboxRight
        }
    }
}
