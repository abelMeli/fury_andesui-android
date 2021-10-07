package com.mercadolibre.android.andesui.moneyamount.type

import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.color.AndesColor
import com.mercadolibre.android.andesui.color.toAndesColor

internal interface AndesMoneyAmountTypeInterface {
    fun getSign(): String
    fun getTextColor(): AndesColor
    fun setStrikeThrough(spannableString: SpannableString)
}

internal object AndesMoneyAmountTypePositive : AndesMoneyAmountTypeInterface {
    override fun getSign(): String = WITH_OUT_SIGN
    override fun getTextColor() = R.color.andes_text_color_primary.toAndesColor()
    override fun setStrikeThrough(spannableString: SpannableString) {
        // Nothing to do
    }
}

internal object AndesMoneyAmountTypeNegative : AndesMoneyAmountTypeInterface {
    override fun getSign(): String = NEGATIVE_SIGN
    override fun getTextColor() = R.color.andes_text_color_primary.toAndesColor()
    override fun setStrikeThrough(spannableString: SpannableString) {
        // Nothing to do
    }
}

internal object AndesMoneyAmountTypePrevious : AndesMoneyAmountTypeInterface {
    override fun getSign(): String = WITH_OUT_SIGN
    override fun getTextColor() = R.color.andes_text_color_secondary.toAndesColor()
    override fun setStrikeThrough(spannableString: SpannableString) {
        spannableString.setSpan(StrikethroughSpan(), 0, spannableString.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
}

private const val WITH_OUT_SIGN = ""
private const val NEGATIVE_SIGN = "- "
