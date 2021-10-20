package com.mercadolibre.android.andesui.moneyamount.combosize

enum class AndesMoneyAmountComboSize {
    SIZE_24,
    SIZE_36;

    companion object {
        fun fromString(value: String): AndesMoneyAmountComboSize = valueOf(value.toUpperCase())
    }

    internal val size get() = getAndesMoneyAmountComboSize()

    private fun getAndesMoneyAmountComboSize(): AndesMoneyAmountComboSizeInterface {
        return when (this) {
            SIZE_24 -> AndesMoneyAmountComboSize24
            SIZE_36 -> AndesMoneyAmountComboSize36
        }
    }
}