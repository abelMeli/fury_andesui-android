package com.mercadolibre.android.andesui.amountfield.accessibility

import android.content.res.Resources
import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.amountfield.AndesAmountFieldSimple
import com.mercadolibre.android.andesui.amountfield.entrytype.AndesAmountFieldEntryType
import com.mercadolibre.android.andesui.amountfield.state.AndesAmountFieldState
import com.mercadolibre.android.andesui.currency.AndesCurrencyHelper
import com.mercadolibre.android.andesui.currency.AndesCurrencyInfo

internal class AndesAmountFieldSimpleMoneyAccessibilityDelegate(
    private val component: AndesAmountFieldSimple
) : View.AccessibilityDelegate() {

    override fun onInitializeAccessibilityNodeInfo(host: View?, info: AccessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(host, info)
        info.contentDescription = generateContentDescription()
    }

    private fun generateContentDescription(): String {
        val currencyInfo = AndesCurrencyHelper.getCurrency(component.currency)
        val resources = component.context.resources
        val isTypePercentage = component.entryType == AndesAmountFieldEntryType.PERCENTAGE

        val currencyText = resolveCurrencyText(resources, currencyInfo, isTypePercentage)
        val suffixText = resolveSuffixText(isTypePercentage)
        val errorText = resolveErrorText(resources)
        val helperText = resolveHelperText(resources)

        return "$currencyText $suffixText, $errorText, $helperText"
    }

    private fun resolveCurrencyText(
        resources: Resources,
        currencyInfo: AndesCurrencyInfo,
        isTypePercentage: Boolean
    ): CharSequence {
        return if (isTypePercentage) {
            EMPTY_STRING
        } else {
            resources.getString(currencyInfo.pluralDescription ?: DEFAULT_CURRENCY_DESCRIPTION)
        }
    }

    private fun resolveSuffixText(isTypePercentage: Boolean): CharSequence? {
        return if (isTypePercentage) {
            PERCENTAGE_STRING
        } else {
            component.suffixA11yText
                ?.takeIf { it.isNotEmpty() }
                ?: component.suffixText
                ?: EMPTY_STRING
        }
    }

    private fun resolveErrorText(resources: Resources): String {
        return when (component.state) {
            AndesAmountFieldState.AmountExceeded, AndesAmountFieldState.Error -> {
                resources.getString(R.string.andes_amount_field_error_text)
            }
            else -> EMPTY_STRING
        }
    }

    private fun resolveHelperText(resources: Resources): String {
        return when (component.state) {
            AndesAmountFieldState.AmountExceeded -> {
                resources.getString(R.string.andes_amount_field_exceeded_text)
            }
            else -> component.helperText?.toString() ?: EMPTY_STRING
        }
    }

    private companion object {
        private val DEFAULT_CURRENCY_DESCRIPTION = R.string.andes_currency_peso_plural
        private const val EMPTY_STRING = ""
        private const val PERCENTAGE_STRING = "%"
    }
}
