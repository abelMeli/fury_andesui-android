package com.mercadolibre.android.andesui.moneyamount.decimalstyle

enum class AndesMoneyAmountDecimalsStyle {
    NONE,
    NORMAL,
    SUPERSCRIPT;

    companion object {
        fun fromString(value: String): AndesMoneyAmountDecimalsStyle = valueOf(value.toUpperCase())
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
