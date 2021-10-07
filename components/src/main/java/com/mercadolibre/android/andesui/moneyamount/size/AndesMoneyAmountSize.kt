package com.mercadolibre.android.andesui.moneyamount.size

enum class AndesMoneyAmountSize {
    SIZE_12,
    SIZE_14,
    SIZE_16,
    SIZE_18,
    SIZE_20,
    SIZE_24,
    SIZE_28,
    SIZE_32,
    SIZE_36,
    SIZE_40,
    SIZE_44,
    SIZE_48,
    SIZE_52,
    SIZE_56,
    SIZE_60;

    companion object {
        fun fromString(value: String): AndesMoneyAmountSize = valueOf(value.toUpperCase())
    }

    internal val size get() = getAndesMoneyAmountSize()

    @Suppress("ComplexMethod")
    private fun getAndesMoneyAmountSize(): AndesMoneyAmountSizeInterface {
        return when (this) {
            SIZE_12 -> AndesMoneyAmountSize12
            SIZE_14 -> AndesMoneyAmountSize14
            SIZE_16 -> AndesMoneyAmountSize16
            SIZE_18 -> AndesMoneyAmountSize18
            SIZE_20 -> AndesMoneyAmountSize20
            SIZE_24 -> AndesMoneyAmountSize24
            SIZE_28 -> AndesMoneyAmountSize28
            SIZE_32 -> AndesMoneyAmountSize32
            SIZE_36 -> AndesMoneyAmountSize36
            SIZE_40 -> AndesMoneyAmountSize40
            SIZE_44 -> AndesMoneyAmountSize44
            SIZE_48 -> AndesMoneyAmountSize48
            SIZE_52 -> AndesMoneyAmountSize52
            SIZE_56 -> AndesMoneyAmountSize56
            SIZE_60 -> AndesMoneyAmountSize60
        }
    }
}
