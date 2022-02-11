package com.mercadolibre.android.andesui.radiobutton.type

import java.util.Locale

enum class AndesRadioButtonType {
    IDLE,
    DISABLED,
    ERROR;

    companion object {
        fun fromString(value: String): AndesRadioButtonType = valueOf(value.toUpperCase(Locale.ROOT))
    }

    internal val type get() = getAndesRadioButtonType()

    private fun getAndesRadioButtonType(): AndesRadioButtonTypeInterface {
        return when (this) {
            IDLE -> AndesRadioButtonTypeIdle
            DISABLED -> AndesRadioButtonTypeDisabled
            ERROR -> AndesRadioButtonTypeError
        }
    }
}
