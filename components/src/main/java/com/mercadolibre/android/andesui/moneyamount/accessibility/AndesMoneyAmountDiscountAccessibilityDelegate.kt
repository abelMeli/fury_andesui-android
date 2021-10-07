package com.mercadolibre.android.andesui.moneyamount.accessibility

import android.content.res.Resources
import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.moneyamount.AndesMoneyAmountDiscount

internal class AndesMoneyAmountDiscountAccessibilityDelegate(private val andesMoneyAmountDiscount: AndesMoneyAmountDiscount) :
    View.AccessibilityDelegate() {

    override fun onInitializeAccessibilityNodeInfo(host: View?, info: AccessibilityNodeInfo?) {
        super.onInitializeAccessibilityNodeInfo(host, info)
        info?.contentDescription = generateMoneyAmountDiscountContentDescriptionText(
            andesMoneyAmountDiscount.discount, andesMoneyAmountDiscount.resources
        )
    }

    companion object {
        internal fun generateMoneyAmountDiscountContentDescriptionText(discount: Int, resources: Resources): String {
            return "$discount ${resources.getString(R.string.andes_money_amount_discount_accessibility)}"
        }
    }
}
