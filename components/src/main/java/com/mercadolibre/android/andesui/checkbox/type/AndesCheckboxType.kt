package com.mercadolibre.android.andesui.checkbox.type

import java.util.Locale

enum class AndesCheckboxType {
    IDLE,
    DISABLED,
    ERROR;

    companion object {
        fun fromString(value: String): AndesCheckboxType = valueOf(value.toUpperCase(Locale.ROOT))
    }

    internal val type get() = getAndesCheckboxType()

    private fun getAndesCheckboxType(): AndesCheckboxTypeInterface {
        return when (this) {
            IDLE -> AndesCheckboxTypeIdle
            DISABLED -> AndesCheckboxTypeDisabled
            ERROR -> AndesCheckboxTypeError
        }
    }
}
