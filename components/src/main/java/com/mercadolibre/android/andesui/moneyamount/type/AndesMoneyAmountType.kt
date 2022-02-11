package com.mercadolibre.android.andesui.moneyamount.type

import java.util.Locale

enum class AndesMoneyAmountType {
    POSITIVE,
    NEGATIVE,
    PREVIOUS;

    companion object {
        fun fromString(value: String): AndesMoneyAmountType = valueOf(value.toUpperCase(Locale.ROOT))
    }

    internal val type get() = getAndesMoneyAmountType()

    private fun getAndesMoneyAmountType(): AndesMoneyAmountTypeInterface {
        return when (this) {
            POSITIVE -> AndesMoneyAmountTypePositive
            NEGATIVE -> AndesMoneyAmountTypeNegative
            PREVIOUS -> AndesMoneyAmountTypePrevious
        }
    }
}
