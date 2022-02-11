package com.mercadolibre.android.andesui.moneyamount.decimalstyle

import android.text.SpannableStringBuilder
import android.text.Spanned
import com.mercadolibre.android.andesui.currency.AndesCountryInfo
import com.mercadolibre.android.andesui.currency.AndesCurrencyInfo
import com.mercadolibre.android.andesui.moneyamount.MoneyAmountUtils
import com.mercadolibre.android.andesui.moneyamount.MoneyAmountUtils.formatAmount
import com.mercadolibre.android.andesui.utils.isInt

internal interface AndesMoneyAmountDecimalsStyleInterface {
    fun getAmountStyled(amount: Double, country: AndesCountryInfo, currency: AndesCurrencyInfo,
                        showZerosDecimal: Boolean, superScriptSize: Float): SpannableStringBuilder
}

internal object AndesMoneyAmountStyleNone : AndesMoneyAmountDecimalsStyleInterface {
    override fun getAmountStyled(amount: Double, country: AndesCountryInfo, currency: AndesCurrencyInfo,
                                 showZerosDecimal: Boolean, superScriptSize: Float): SpannableStringBuilder {
        formatAmount(
            amount = amount,
            decimalPlaces = 0,
            currencySymbol = currency.symbol,
            country = country
        ).apply {
            return SpannableStringBuilder(this)
        }
    }
}

internal object AndesMoneyAmountStyleNormal : AndesMoneyAmountDecimalsStyleInterface {
    override fun getAmountStyled(amount: Double, country: AndesCountryInfo, currency: AndesCurrencyInfo,
                                 showZerosDecimal: Boolean, superScriptSize: Float): SpannableStringBuilder {
        var decimalPlaces = currency.decimalPlaces
        if (amount.isInt() && !showZerosDecimal && !currency.isCrypto) {
            decimalPlaces = 0
        }
        formatAmount(
            amount = amount,
            decimalPlaces = decimalPlaces,
            currencySymbol = currency.symbol,
            country = country
        ).apply {
            return SpannableStringBuilder(this)
        }
    }
}

internal object AndesMoneyAmountStyleSuperScript : AndesMoneyAmountDecimalsStyleInterface {
    override fun getAmountStyled(amount: Double, country: AndesCountryInfo, currency: AndesCurrencyInfo,
                                 showZerosDecimal: Boolean, superScriptSize: Float): SpannableStringBuilder {
        if (amount.isInt() && !showZerosDecimal) {
            return SpannableStringBuilder(formatAmount(
                amount = amount,
                decimalPlaces = 0,
                currencySymbol = currency.symbol,
                country = country
            ))
        } else {
            var formatAmount = formatAmount(
                amount = amount,
                decimalPlaces = currency.decimalPlaces,
                currencySymbol = currency.symbol,
                country = country
            )
            formatAmount = formatAmount.replace(country.decimalSeparator.toString(), "")
            return SpannableStringBuilder(formatAmount).apply {
                setSpan(
                    MoneyAmountUtils.CustomRelativeSizeSpan(superScriptSize),
                    length - currency.decimalPlaces,
                    length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
    }
}
