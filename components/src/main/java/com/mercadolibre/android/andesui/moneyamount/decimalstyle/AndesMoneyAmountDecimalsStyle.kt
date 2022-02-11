package com.mercadolibre.android.andesui.moneyamount.decimalstyle

import java.util.Locale

enum class AndesMoneyAmountDecimalsStyle {
    NONE,
    NORMAL,
    SUPERSCRIPT;

    companion object {
        fun fromString(value: String): AndesMoneyAmountDecimalsStyle = valueOf(value.toUpperCase(Locale.ROOT))
    }

    internal val style get() = getAndesMoneyAmountDecimalsStyle()

    private fun getAndesMoneyAmountDecimalsStyle(): AndesMoneyAmountDecimalsStyleInterface {
        return when (this) {
            NONE -> AndesMoneyAmountStyleNone
            NORMAL -> AndesMoneyAmountStyleNormal
            SUPERSCRIPT -> AndesMoneyAmountStyleSuperScript
        }
    }
}
