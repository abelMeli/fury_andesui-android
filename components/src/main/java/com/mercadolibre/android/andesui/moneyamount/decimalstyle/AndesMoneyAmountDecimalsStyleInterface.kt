package com.mercadolibre.android.andesui.moneyamount.decimalstyle

import android.text.SpannableString
import android.text.Spanned
import com.mercadolibre.android.andesui.moneyamount.MoneyAmountUtils
import com.mercadolibre.android.andesui.utils.isInt

internal interface AndesMoneyAmountDecimalsStyleInterface {
    @Suppress("LongParameterList")
    fun setStyle(
        spannable: SpannableString,
        superScriptSize: Float,
        decimalPlaces: Int,
        decimalSeparator: Char,
        amount: Double,
        showZerosDecimal: Boolean
    ): SpannableString = spannable

    fun getDecimalPlaces(decimalPlaces: Int, showZerosDecimal: Boolean, amount: Double): Int {
        return 0.takeIf { !showZerosDecimal && amount.isInt() } ?: decimalPlaces
    }
}

internal object AndesMoneyAmountStyleNone : AndesMoneyAmountDecimalsStyleInterface {
    override fun getDecimalPlaces(
        decimalPlaces: Int,
        showZerosDecimal: Boolean,
        amount: Double
    ): Int = 0
}

internal object AndesMoneyAmountStyleNormal : AndesMoneyAmountDecimalsStyleInterface

internal object AndesMoneyAmountStyleSuperScript : AndesMoneyAmountDecimalsStyleInterface {
    override fun setStyle(
        spannable: SpannableString,
        superScriptSize: Float,
        decimalPlaces: Int,
        decimalSeparator: Char,
        amount: Double,
        showZerosDecimal: Boolean
    ): SpannableString {
        return if (!amount.isInt() || showZerosDecimal) {
            with(spannable) {
                SpannableString(
                    removeRange(indexOf(decimalSeparator), indexOf(decimalSeparator) + 1)
                ).apply {
                    setSpan(
                        MoneyAmountUtils.CustomRelativeSizeSpan(superScriptSize),
                        length - decimalPlaces,
                        length,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
            }
        } else {
            spannable
        }
    }
}
