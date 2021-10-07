package com.mercadolibre.android.andesui.moneyamount.combosize

import android.content.Context
import com.mercadolibre.android.andesui.moneyamount.size.AndesMoneyAmountSize

internal interface AndesMoneyAmountComboSizeInterface {
    fun amountSize(context: Context): AndesMoneyAmountSize
    fun previousAmountSize(context: Context): AndesMoneyAmountSize
    fun discountSize(context: Context): AndesMoneyAmountSize
}

internal object AndesMoneyAmountComboSize24 : AndesMoneyAmountComboSizeInterface {
    override fun amountSize(context: Context) = AndesMoneyAmountSize.SIZE_24
    override fun previousAmountSize(context: Context) = AndesMoneyAmountSize.SIZE_12
    override fun discountSize(context: Context) = AndesMoneyAmountSize.SIZE_14
}

internal object AndesMoneyAmountComboSize36 : AndesMoneyAmountComboSizeInterface {
    override fun amountSize(context: Context) = AndesMoneyAmountSize.SIZE_36
    override fun previousAmountSize(context: Context) = AndesMoneyAmountSize.SIZE_16
    override fun discountSize(context: Context) = AndesMoneyAmountSize.SIZE_18
}
