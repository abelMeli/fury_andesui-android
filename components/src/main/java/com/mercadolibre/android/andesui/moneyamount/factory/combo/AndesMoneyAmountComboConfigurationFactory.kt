package com.mercadolibre.android.andesui.moneyamount.factory.combo

import android.content.Context
import com.mercadolibre.android.andesui.country.AndesCountry
import com.mercadolibre.android.andesui.moneyamount.currency.AndesMoneyAmountCurrency
import com.mercadolibre.android.andesui.moneyamount.size.AndesMoneyAmountSize

internal data class AndesMoneyAmountComboConfiguration(
    val amount: Double,
    val previousAmount: Double,
    val discount: Int,
    val discountSize: AndesMoneyAmountSize,
    val previousAmountSize: AndesMoneyAmountSize,
    val amountSize: AndesMoneyAmountSize,
    val country: AndesCountry,
    val currency: AndesMoneyAmountCurrency
)

internal object AndesMoneyAmountComboConfigurationFactory {

    fun create(context: Context, andesMoneyAmountComboAttrs: AndesMoneyAmountComboAttrs): AndesMoneyAmountComboConfiguration {
        return with(andesMoneyAmountComboAttrs) {
            AndesMoneyAmountComboConfiguration(
                amount = andesMoneyAmount,
                previousAmount = andesMoneyPreviousAmount,
                discount = andesMoneyDiscount,
                discountSize = andesMoneyAmountSize.size.discountSize(context),
                previousAmountSize = andesMoneyAmountSize.size.previousAmountSize(context),
                amountSize = andesMoneyAmountSize.size.amountSize(context),
                country = andesMoneyAmountComboAttrs.andesMoneyAmountCountry,
                currency = andesMoneyAmountComboAttrs.andesMoneyAmountCurrency
            )
        }
    }
}
