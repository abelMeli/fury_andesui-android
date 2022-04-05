package com.mercadolibre.android.andesui.amountfield.factory

import android.content.Context
import android.text.TextWatcher
import android.view.ViewGroup
import com.mercadolibre.android.andesui.amountfield.entrymode.AndesAmountFieldEntryMode
import com.mercadolibre.android.andesui.amountfield.state.AndesAmountFieldState
import com.mercadolibre.android.andesui.amountfield.utils.AmountListener
import com.mercadolibre.android.andesui.amountfield.utils.ResizingListener
import com.mercadolibre.android.andesui.country.AndesCountry
import com.mercadolibre.android.andesui.currency.AndesCountryInfo
import com.mercadolibre.android.andesui.currency.AndesCurrencyHelper
import com.mercadolibre.android.andesui.currency.AndesCurrencyInfo
import com.mercadolibre.android.andesui.moneyamount.currency.AndesMoneyAmountCurrency
import com.mercadolibre.android.andesui.textview.color.AndesTextViewColor

internal data class AndesAmountFieldSimpleMoneyConfiguration(
    val formatter: TextWatcher,
    val maxValue: String?,
    val numberOfDecimals: Int,
    val decimalSeparator: Char,
    val textAlignment: Int,
    val initialValue: String?,
    val placeholder: CharSequence,
    val suffixText: CharSequence?,
    val suffixTextColor: AndesTextViewColor,
    val stateActions: (ViewGroup) -> Unit,
    val helperText: CharSequence?,
    val helperTextColor: AndesTextViewColor,
    val helperTextStyle: Int,
    val helperIconVisibility: Int,
    val currencySymbol: CharSequence,
    val currencyVisibility: Int,
    val editTextSize: Float,
    val currencyTextSize: Float,
    val suffixTextSize: Float,
    val resizableComponentsHorizontalMargin: Int
)

internal object AndesAmountFieldSimpleMoneyConfigFactory {
    fun create(
        context: Context,
        resizingListener: ResizingListener,
        amountListener: AmountListener,
        attrs: AndesAmountFieldSimpleMoneyAttrs
    ): AndesAmountFieldSimpleMoneyConfiguration {
        return with(attrs) {
            val currencyInfo = AndesCurrencyHelper.getCurrency(andesAmountFieldCurrency)
            val countryInfo = AndesCurrencyHelper.getCountry(andesAmountFieldCountry)
            val maxValue = andesAmountFieldMaxValue
            val numberOfDecimals = andesAmountFieldNumberOfDecimals ?: currencyInfo.decimalPlaces
            val entryMode = resolveEntryMode(
                andesAmountFieldEntryMode,
                andesAmountFieldCountry,
                numberOfDecimals
            )
            val isInputBlocked = andesAmountFieldState == AndesAmountFieldState.AmountExceeded
            AndesAmountFieldSimpleMoneyConfiguration(
                formatter = entryMode.entryMode.getFormatter(
                    resizingListener,
                    amountListener,
                    countryInfo,
                    numberOfDecimals,
                    maxValue,
                    isInputBlocked
                ),
                maxValue = maxValue,
                numberOfDecimals = numberOfDecimals,
                decimalSeparator = countryInfo.decimalSeparator,
                textAlignment = entryMode.entryMode.getTextAlignment(),
                initialValue = andesAmountFieldInitialValue,
                placeholder = entryMode.entryMode.getPlaceholder(
                    context,
                    countryInfo,
                    numberOfDecimals
                ),
                suffixText = andesAmountFieldEntryType.entryType.getSuffixText(andesAmountFieldSuffixText),
                suffixTextColor = andesAmountFieldEntryType.entryType.getSuffixTextColor(),
                stateActions = andesAmountFieldState.state.getActions(),
                helperText = resolveHelperText(context, andesAmountFieldHelperText, andesAmountFieldState),
                helperTextColor = andesAmountFieldState.state.getHelperTextColor(),
                helperTextStyle = andesAmountFieldState.state.getHelperTextStyle(),
                helperIconVisibility = andesAmountFieldState.state.getIconVisibility(),
                currencySymbol = resolveCurrencySymbol(andesAmountFieldCurrency, andesAmountFieldShowCurrencyAsIsoValue),
                currencyVisibility = andesAmountFieldEntryType.entryType.getCurrencyVisibility(),
                editTextSize = andesAmountFieldSize.size.amountSize(context),
                currencyTextSize = andesAmountFieldSize.size.currencySize(context),
                suffixTextSize = andesAmountFieldSize.size.suffixSize(context),
                resizableComponentsHorizontalMargin = andesAmountFieldSize.size.horizontalMargin(context)
            )
        }
    }

    private fun resolveCurrencySymbol(
        currency: AndesMoneyAmountCurrency,
        showCurrencyAsIsoValue: Boolean
    ): String {
        val currencyInfo = AndesCurrencyHelper.getCurrency(currency)
        return if (showCurrencyAsIsoValue) {
            currency.name
        } else {
            currencyInfo.symbol
        }
    }

    private fun resolveHelperText(
        context: Context,
        andesAmountFieldHelperText: CharSequence?,
        andesAmountFieldState: AndesAmountFieldState
    ): CharSequence? {
        return if (andesAmountFieldState.state.getExceededHelperText(context) != null) {
            andesAmountFieldState.state.getExceededHelperText(context)
        } else {
            andesAmountFieldHelperText
        }
    }

    private fun resolveEntryMode(
        entryMode: AndesAmountFieldEntryMode?,
        country: AndesCountry,
        numberOfDecimals: Int
    ): AndesAmountFieldEntryMode {
        if (numberOfDecimals == 0) {
            return AndesAmountFieldEntryMode.INT
        }

        if (entryMode != null) {
            return entryMode
        }

        return if (country == AndesCountry.BR) {
            AndesAmountFieldEntryMode.DECIMAL
        } else {
            AndesAmountFieldEntryMode.INT
        }
    }
}
