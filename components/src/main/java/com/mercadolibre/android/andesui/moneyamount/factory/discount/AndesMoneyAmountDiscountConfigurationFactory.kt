package com.mercadolibre.android.andesui.moneyamount.factory.discount

import android.content.Context
import android.graphics.drawable.Drawable
import com.mercadolibre.android.andesui.R

internal data class AndesMoneyAmountDiscountConfiguration(
    val discount: Int,
    val discountSize: Float,
    val discountIcon: Drawable?,
    val discountIconSize: Float,
    val iconPadding: Float
)

internal object AndesMoneyAmountDiscountConfigurationFactory {

    fun create(context: Context, andesMoneyAmountDiscountAttrs: AndesMoneyAmountDiscountAttrs): AndesMoneyAmountDiscountConfiguration {
        return with(andesMoneyAmountDiscountAttrs) {
            AndesMoneyAmountDiscountConfiguration(
                discount = discount,
                discountSize = andesMoneyAmountDiscountAttrs.discountSize.size.textSize(context),
                discountIconSize = andesMoneyAmountDiscountAttrs.discountSize.size.discountIconSize(context),
                iconPadding = context.resources.getDimension(R.dimen.andes_money_amount_padding_4),
                discountIcon = discountIcon
            )
        }
    }
}
