package com.mercadolibre.android.andesui.moneyamount.decimalstyle

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import com.mercadolibre.android.andesui.currency.AndesCountryInfo
import com.mercadolibre.android.andesui.currency.AndesCurrencyInfo
import com.mercadolibre.android.andesui.moneyamount.MoneyAmountUtils
import com.mercadolibre.android.andesui.moneyamount.MoneyAmountUtils.formatAmount
import com.mercadolibre.android.andesui.utils.isInt

internal interface AndesMoneyAmountDecimalsStyleInterface {
    fun getAmountStyled(
        amount: Double,
        country: AndesCountryInfo,
        currency: AndesCurrencyInfo,
        showZerosDecimal: Boolean,
        superScriptSize: Float,
        amountSize: Float
    ): SpannableStringBuilder
}

internal object AndesMoneyAmountStyleNone : AndesMoneyAmountDecimalsStyleInterface {
    override fun getAmountStyled(
        amount: Double, country:
        AndesCountryInfo,
        currency: AndesCurrencyInfo,
        showZerosDecimal: Boolean,
        superScriptSize: Float,
        amountSize: Float
    ): SpannableStringBuilder {
        return formatAmount(
            amount = amount,
            decimalPlaces = 0,
            currencySymbol = currency.symbol,
            country = country,
            amountSize = amountSize.toInt()
        )
    }
}

internal object AndesMoneyAmountStyleNormal : AndesMoneyAmountDecimalsStyleInterface {
    override fun getAmountStyled(
        amount: Double,
        country: AndesCountryInfo,
        currency: AndesCurrencyInfo,
        showZerosDecimal: Boolean,
        superScriptSize: Float,
        amountSize: Float
    ): SpannableStringBuilder {
        var decimalPlaces = currency.decimalPlaces
        if (amount.isInt() && !showZerosDecimal && !currency.isCrypto) {
            decimalPlaces = 0
        }
        return formatAmount(
            amount = amount,
            decimalPlaces = decimalPlaces,
            currencySymbol = currency.symbol,
            country = country,
            amountSize = amountSize.toInt()
        )
    }
}

internal object AndesMoneyAmountStyleSuperScript : AndesMoneyAmountDecimalsStyleInterface {
    override fun getAmountStyled(
        amount: Double,
        country: AndesCountryInfo,
        currency: AndesCurrencyInfo,
        showZerosDecimal: Boolean,
        superScriptSize: Float,
        amountSize: Float
    ): SpannableStringBuilder {
        if (amount.isInt() && !showZerosDecimal) {
            return formatAmount(
                amount = amount,
                decimalPlaces = 0,
                currencySymbol = currency.symbol,
                country = country,
                amountSize = amountSize.toInt()
            )
        } else {
            return formatAmount(
                amount = amount,
                decimalPlaces = currency.decimalPlaces,
                currencySymbol = currency.symbol,
                country = country,
                amountSize = amountSize.toInt(),
                transform = {
                    it.replace(country.decimalSeparator.toString(), "")
                }
            ).apply {
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
