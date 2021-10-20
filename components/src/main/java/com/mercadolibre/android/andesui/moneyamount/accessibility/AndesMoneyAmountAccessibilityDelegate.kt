package com.mercadolibre.android.andesui.moneyamount.accessibility

import android.content.res.Resources
import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.currency.AndesCountryInfo
import com.mercadolibre.android.andesui.currency.AndesCurrencyHelper
import com.mercadolibre.android.andesui.currency.AndesCurrencyInfo
import com.mercadolibre.android.andesui.moneyamount.AndesMoneyAmount
import com.mercadolibre.android.andesui.moneyamount.MoneyAmountUtils
import com.mercadolibre.android.andesui.moneyamount.type.AndesMoneyAmountType
import java.text.DecimalFormatSymbols

internal class AndesMoneyAmountAccessibilityDelegate constructor(
    private val andesMoneyAmount: AndesMoneyAmount
) : View.AccessibilityDelegate() {

    override fun onInitializeAccessibilityNodeInfo(host: View?, info: AccessibilityNodeInfo?) {
        super.onInitializeAccessibilityNodeInfo(host, info)
        val currencyInfo = AndesCurrencyHelper.getCurrency(andesMoneyAmount.currency)
        val countryInfo = AndesCurrencyHelper.getCountry(andesMoneyAmount.country)
        info?.contentDescription = generateMoneyAmountContentDescriptionText(
            andesMoneyAmount.resources,
            andesMoneyAmount.type,
            currencyInfo,
            andesMoneyAmount.amount,
            isCombo = false,
            showZerosDecimal = andesMoneyAmount.showZerosDecimal,
            countryInfo = countryInfo
        )
    }

    companion object {
        private const val EMPTY = ""
        private const val ONE = "1"
        private const val ZERO = "0"

        @Suppress("LongParameterList")
        internal fun generateMoneyAmountContentDescriptionText(
            resources: Resources,
            type: AndesMoneyAmountType,
            currency: AndesCurrencyInfo,
            amount: Double,
            isCombo: Boolean,
            showZerosDecimal: Boolean,
            countryInfo: AndesCountryInfo
        ): String {
            val currencyMessage = generateAmountText(resources, currency, amount, showZerosDecimal, countryInfo)
            val negativeMessage = generateNegativeDescriptionText(resources, type)
            val previousMessage = generatePreviousDescriptionText(resources, type, isCombo)
            return "$previousMessage $negativeMessage $currencyMessage".trim()
        }

        private fun generateAmountText(
            resources: Resources,
            currency: AndesCurrencyInfo,
            amountDouble: Double,
            showZerosDecimal: Boolean,
            countryInfo: AndesCountryInfo
        ): String {
            val splittedAmount = getSplittedAmount(amountDouble, currency, countryInfo)

            val amount = splittedAmount[0]
            var decimal = EMPTY
            if (splittedAmount.size > 1) {
                decimal = splittedAmount[1].toInt().toString()
            }

            var result = if (amount == ONE) {
                "${resources.getString(R.string.andes_money_amount_one)} ${resources.getString(currency.singularDescription)}"
            } else {
                "$amount ${resources.getString(currency.pluralDescription)}"
            }

            if (decimal == ONE) {
                result += " ${resources.getString(R.string.andes_money_amount_with_accessibility)} " +
                        "${resources.getString(R.string.andes_money_amount_one)} ${resources.getString(currency.decimalSingularDescription)}"
            } else if (decimal != EMPTY && (decimal != ZERO || showZerosDecimal)) {
                result += " ${resources.getString(R.string.andes_money_amount_with_accessibility)} " +
                        "$decimal ${resources.getString(currency.decimalPluralDescription)}"
            }

            return result
        }

        private fun getSplittedAmount(amount: Double, currency: AndesCurrencyInfo, countryInfo: AndesCountryInfo): List<String> {
            return MoneyAmountUtils.getLocatedDecimalFormat(
                currency.decimalPlaces, DecimalFormatSymbols().apply {
                    decimalSeparator = countryInfo.decimalSeparator
                }
            ).let { decimalFormat ->
                decimalFormat.isGroupingUsed = false
                decimalFormat.format(amount).split(countryInfo.decimalSeparator)
            }
        }

        private fun generatePreviousDescriptionText(resources: Resources, type: AndesMoneyAmountType, isCombo: Boolean): String {
            return if (type == AndesMoneyAmountType.PREVIOUS && isCombo) {
                resources.getString(R.string.andes_money_amount_previous_combo_accessibility)
            } else if (type == AndesMoneyAmountType.PREVIOUS && !isCombo) {
                resources.getString(R.string.andes_money_amount_previous_accessibility)
            } else {
                EMPTY
            }
        }

        private fun generateNegativeDescriptionText(resources: Resources, type: AndesMoneyAmountType): String {
            return if (type == AndesMoneyAmountType.NEGATIVE) {
                resources.getString(R.string.andes_money_amount_negative_accessibility)
            } else {
                EMPTY
            }
        }
    }
}