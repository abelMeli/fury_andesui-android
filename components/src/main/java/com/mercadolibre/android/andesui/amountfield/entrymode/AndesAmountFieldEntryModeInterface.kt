package com.mercadolibre.android.andesui.amountfield.entrymode

import android.content.Context
import android.text.TextWatcher
import android.view.View
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.amountfield.utils.AmountListener
import com.mercadolibre.android.andesui.amountfield.utils.AndesAmountFieldDecimalFormatter
import com.mercadolibre.android.andesui.amountfield.utils.AndesAmountFieldIntFormatter
import com.mercadolibre.android.andesui.amountfield.utils.ResizingListener
import com.mercadolibre.android.andesui.currency.AndesCountryInfo
import com.mercadolibre.android.andesui.utils.append

internal interface AndesAmountFieldEntryModeInterface {
    fun getPlaceholder(
        context: Context,
        countryInfo: AndesCountryInfo,
        decimalPlaces: Int
    ): CharSequence
    fun getFormatter(
        resizingListener: ResizingListener,
        amountListener: AmountListener,
        countryInfo: AndesCountryInfo,
        decimalPlaces: Int,
        maxValue: String?,
        isBlocked: Boolean
    ): TextWatcher
    fun getTextAlignment(): Int
}

internal object AndesAmountFieldEntryModeInt : AndesAmountFieldEntryModeInterface {
    override fun getPlaceholder(
        context: Context,
        countryInfo: AndesCountryInfo,
        decimalPlaces: Int
    ) = context.resources.getString(R.string.andes_amount_field_int_placeholder)

    override fun getFormatter(
        resizingListener: ResizingListener,
        amountListener: AmountListener,
        countryInfo: AndesCountryInfo,
        decimalPlaces: Int,
        maxValue: String?,
        isBlocked: Boolean
    ) = AndesAmountFieldIntFormatter(
        resizingListener,
        amountListener,
        countryInfo.decimalSeparator,
        decimalPlaces,
        maxValue,
        isBlocked
    )

    override fun getTextAlignment() = View.TEXT_ALIGNMENT_TEXT_START
}

internal object AndesAmountFieldEntryModeDecimal : AndesAmountFieldEntryModeInterface {
    override fun getPlaceholder(
        context: Context,
        countryInfo: AndesCountryInfo,
        decimalPlaces: Int
    ): CharSequence {
        if (decimalPlaces == 0) return context.resources.getString(R.string.andes_amount_field_int_placeholder)
        var zerosInDecimalPart: CharSequence = EMPTY_STRING
        (1..decimalPlaces).forEach { _ ->
            zerosInDecimalPart = zerosInDecimalPart.append(ZERO)
        }
        return context.resources.getString(
            R.string.andes_amount_field_decimal_placeholder,
            countryInfo.decimalSeparator,
            zerosInDecimalPart
        )
    }

    override fun getFormatter(
        resizingListener: ResizingListener,
        amountListener: AmountListener,
        countryInfo: AndesCountryInfo,
        decimalPlaces: Int,
        maxValue: String?,
        isBlocked: Boolean
    ) = AndesAmountFieldDecimalFormatter(
        resizingListener,
        amountListener,
        countryInfo.decimalSeparator,
        decimalPlaces,
        maxValue,
        isBlocked
    )

    override fun getTextAlignment() = View.TEXT_ALIGNMENT_TEXT_END

    private const val EMPTY_STRING = ""
    private const val ZERO = '0'
}
