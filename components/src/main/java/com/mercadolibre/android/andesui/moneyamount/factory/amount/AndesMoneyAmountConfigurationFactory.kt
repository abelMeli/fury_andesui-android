package com.mercadolibre.android.andesui.moneyamount.factory.amount

import android.content.Context
import android.text.SpannableString
import com.mercadolibre.android.andesui.color.AndesColor
import com.mercadolibre.android.andesui.currency.AndesCountryInfo
import com.mercadolibre.android.andesui.currency.AndesCurrencyHelper
import com.mercadolibre.android.andesui.currency.AndesCurrencyInfo
import com.mercadolibre.android.andesui.moneyamount.MoneyAmountUtils.formatAmount
import com.mercadolibre.android.andesui.moneyamount.decimalstyle.AndesMoneyAmountDecimalsStyle
import com.mercadolibre.android.andesui.moneyamount.size.AndesMoneyAmountSize
import com.mercadolibre.android.andesui.moneyamount.type.AndesMoneyAmountType

internal data class AndesMoneyAmountConfiguration(
    val amountFormatted: SpannableString,
    val amountSize: Float,
    val color: AndesColor,
    val isValidSize: Boolean,
    val countryInfo: AndesCountryInfo,
    val currencyInfo: AndesCurrencyInfo
)

internal object AndesMoneyAmountConfigurationFactory {

    fun create(context: Context, andesMoneyAmountAttrs: AndesMoneyAmountAttrs): AndesMoneyAmountConfiguration {
        return with(andesMoneyAmountAttrs) {
            val countryInfo = AndesCurrencyHelper.getCountry(andesMoneyAmountAttrs.andesMoneyAmountCountry)
            val currencyInfo = AndesCurrencyHelper.getCurrency(andesMoneyAmountAttrs.andesMoneyAmountCurrency)
            AndesMoneyAmountConfiguration(
                amountFormatted = formatMoneyAmount(
                    andesMoneyAmountType.type.getSign(),
                    andesMoneyAmount,
                    andesShowZerosDecimal,
                    andesMoneyAmountDecimalsStyle,
                    andesMoneyAmountType,
                    currencyInfo,
                    countryInfo,
                    andesMoneyAmountSize.size.superScriptSize(context)
                ),
                amountSize = andesMoneyAmountSize.size.textSize(context),
                color = andesMoneyAmountType.type.getTextColor(),
                isValidSize = resolveValidData(andesMoneyAmountAttrs.andesMoneyAmountDecimalsStyle, andesMoneyAmountAttrs.andesMoneyAmountSize),
                countryInfo = countryInfo,
                currencyInfo = currencyInfo
            )
        }
    }

    private fun resolveValidData(decimalsStyle: AndesMoneyAmountDecimalsStyle, size: AndesMoneyAmountSize): Boolean {
        if (decimalsStyle == AndesMoneyAmountDecimalsStyle.SUPERSCRIPT) {
            if (size == AndesMoneyAmountSize.SIZE_12 || size == AndesMoneyAmountSize.SIZE_14) {
                return false
            }
        }
        return true
    }

    @Suppress("LongParameterList")
    private fun formatMoneyAmount(
        sign: String,
        amount: Double,
        showZerosDecimal: Boolean,
        style: AndesMoneyAmountDecimalsStyle,
        type: AndesMoneyAmountType,
        currency: AndesCurrencyInfo,
        country: AndesCountryInfo,
        superScriptSize: Float
    ): SpannableString {
        return style.style.getDecimalPlaces(currency.decimalPlaces, showZerosDecimal, amount).let { decimalPlaces ->
            SpannableString("$sign${formatAmount(amount, decimalPlaces, currency.symbol, country)}")
        }.let {
            style.style.setStyle(
                it,
                superScriptSize,
                currency.decimalPlaces,
                country.decimalSeparator,
                amount,
                showZerosDecimal
            )
        }.also {
            type.type.setStrikeThrough(it)
        }
    }
}
