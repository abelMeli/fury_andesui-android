package com.mercadolibre.android.andesui.radiobutton.status

import java.util.Locale

enum class AndesRadioButtonStatus {
    SELECTED,
    UNSELECTED;

    companion object {
        fun fromString(value: String): AndesRadioButtonStatus = valueOf(value.toUpperCase(Locale.ROOT))
    }

    internal val status get() = getAndesRadioButtonStatus()

    private fun getAndesRadioButtonStatus(): AndesRadioButtonStatusInterface {
        return when (this) {
            SELECTED -> AndesRadioButtonStatusSelected
            UNSELECTED -> AndesRadioButtonStatusUnselected
        }
    }
}
