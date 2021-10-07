package com.mercadolibre.android.andesui.moneyamount.factory.discount

import android.content.Context

internal data class AndesMoneyAmountDiscountConfiguration(
    val discount: Int,
    val discountSize: Float
)

internal object AndesMoneyAmountDiscountConfigurationFactory {

    fun create(context: Context, andesMoneyAmountDiscountAttrs: AndesMoneyAmountDiscountAttrs): AndesMoneyAmountDiscountConfiguration {
        return with(andesMoneyAmountDiscountAttrs) {
            AndesMoneyAmountDiscountConfiguration(
                    discount = andesMoneyDiscount,
                    discountSize = andesMoneyAmountDiscountAttrs.andesMoneyAmountSize.size.textSize(context)
            )
        }
    }
}
