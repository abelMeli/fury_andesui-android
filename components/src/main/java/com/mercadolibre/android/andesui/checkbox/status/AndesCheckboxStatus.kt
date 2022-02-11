package com.mercadolibre.android.andesui.checkbox.status

import java.util.Locale

enum class AndesCheckboxStatus {
    SELECTED,
    UNSELECTED,
    UNDEFINED;

    companion object {
        fun fromString(value: String): AndesCheckboxStatus = valueOf(value.toUpperCase(Locale.ROOT))
    }

    internal val status get() = getAndesCheckboxStatus()

    private fun getAndesCheckboxStatus(): AndesCheckboxStatusInterface {
        return when (this) {
            SELECTED -> AndesCheckboxStatusSelected
            UNSELECTED -> AndesCheckboxStatusUnselected
            UNDEFINED -> AndesCheckboxStatusUndefined
        }
    }
}
