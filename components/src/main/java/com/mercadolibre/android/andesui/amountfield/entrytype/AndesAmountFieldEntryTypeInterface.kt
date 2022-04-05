package com.mercadolibre.android.andesui.amountfield.entrytype

import android.view.View
import com.mercadolibre.android.andesui.textview.color.AndesTextViewColor

internal interface AndesAmountFieldEntryTypeInterface {
    fun getCurrencyVisibility(): Int
    fun getSuffixText(suffixText: CharSequence?): CharSequence?
    fun getSuffixTextColor(): AndesTextViewColor
}

internal object AndesAmountFieldEntryTypeMoney : AndesAmountFieldEntryTypeInterface {
    override fun getCurrencyVisibility() = View.VISIBLE
    override fun getSuffixText(suffixText: CharSequence?) = suffixText
    override fun getSuffixTextColor() = AndesTextViewColor.Secondary
}

internal object AndesAmountFieldEntryTypePercentage : AndesAmountFieldEntryTypeInterface {
    override fun getCurrencyVisibility() = View.GONE
    override fun getSuffixText(suffixText: CharSequence?) = "%"
    override fun getSuffixTextColor() = AndesTextViewColor.Primary
}
