package com.mercadolibre.android.andesui.textview.moneyamount

import com.mercadolibre.android.andesui.moneyamount.AndesMoneyAmountInfoProvider

/**
 * Store the money amount data to be appended in the andes textview.
 * @property infoProvider AndesMoneyAmountInfoProvider that provides the AndesMoneyAmount data needed
 * to be displayed in textview.
 * @property color Int with the color in text should be displayed.
 */
internal data class AndesTextViewMoneyAmount(val infoProvider: AndesMoneyAmountInfoProvider, val color: Int?)
