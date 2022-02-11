package com.mercadolibre.android.andesui.moneyamount.accessibility

import android.content.res.Resources
import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.currency.AndesCurrencyHelper
import com.mercadolibre.android.andesui.moneyamount.AndesMoneyAmount
import com.mercadolibre.android.andesui.moneyamount.AndesMoneyAmountDiscount
import com.mercadolibre.android.andesui.moneyamount.accessibility.AndesMoneyAmountAccessibilityDelegate.Companion.generateMoneyAmountContentDescriptionText
import com.mercadolibre.android.andesui.moneyamount.accessibility.AndesMoneyAmountDiscountAccessibilityDelegate.Companion.generateMoneyAmountDiscountContentDescriptionText
import com.mercadolibre.android.andesui.moneyamount.currency.AndesMoneyAmountCurrency
import com.mercadolibre.android.andesui.moneyamount.type.AndesMoneyAmountType

internal class AndesMoneyAmountComboAccessibilityDelegate(
    private val amount: AndesMoneyAmount,
    private val previousAmount: AndesMoneyAmount,
    private val andesDiscount: AndesMoneyAmountDiscount,
    private val currency: AndesMoneyAmountCurrency
) : View.AccessibilityDelegate() {

    override fun onInitializeAccessibilityNodeInfo(host: View?, info: AccessibilityNodeInfo?) {
        super.onInitializeAccessibilityNodeInfo(host, info)
        info?.contentDescription = generateContentDescriptionText(amount.resources)
    }

    private fun generateContentDescriptionText(resources: Resources): String {
        val discountMessage = generateDiscountDescriptionText(resources)
        val beforeAmountMessage = generatePreviousAmountDescriptionText(resources)
        val afterAmountMessage = generateAfterAmountDescriptionText(resources)
        return "$afterAmountMessage $discountMessage $beforeAmountMessage"
    }

    private fun generateAfterAmountDescriptionText(resources: Resources): String {
        val currencyInfo = AndesCurrencyHelper.getCurrency(currency)
        val countryInfo = AndesCurrencyHelper.getCountry(amount.country)
        return if (amount.amount != 0.0) {
            "${resources.getString(R.string.andes_money_amount_after_accessibility)} ${generateMoneyAmountContentDescriptionText(
                resources,
                AndesMoneyAmountType.POSITIVE,
                currencyInfo,
                amount.amount,
                isCombo = true,
                showZerosDecimal = false,
                countryInfo = countryInfo,
                suffixAccessibility = ""
            )}$COMMA"
        } else {
            EMPTY
        }
    }

    private fun generatePreviousAmountDescriptionText(resources: Resources): String {
        val currencyInfo = AndesCurrencyHelper.getCurrency(currency)
        val countryInfo = AndesCurrencyHelper.getCountry(amount.country)
        return if (previousAmount.amount != 0.0) {
            generateMoneyAmountContentDescriptionText(
                resources,
                AndesMoneyAmountType.PREVIOUS,
                currencyInfo,
                previousAmount.amount,
                isCombo = true,
                showZerosDecimal = false,
                countryInfo = countryInfo,
                suffixAccessibility = ""
            )
        } else {
            EMPTY
        }
    }

    private fun generateDiscountDescriptionText(resources: Resources): String {
        return if (andesDiscount.discount != 0) {
            "${generateMoneyAmountDiscountContentDescriptionText(andesDiscount.discount, resources)}$COMMA"
        } else {
            EMPTY
        }
    }

    companion object {
        const val EMPTY = ""
        const val COMMA = ","
    }
}
