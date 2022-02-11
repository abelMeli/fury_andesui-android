package com.mercadolibre.android.andesui.moneyamount.factory.amount

import android.content.Context
import android.text.SpannableStringBuilder
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.color.AndesColor
import com.mercadolibre.android.andesui.color.toAndesColor
import com.mercadolibre.android.andesui.currency.AndesCountryInfo
import com.mercadolibre.android.andesui.currency.AndesCurrencyHelper
import com.mercadolibre.android.andesui.currency.AndesCurrencyInfo
import com.mercadolibre.android.andesui.moneyamount.MoneyAmountUtils
import com.mercadolibre.android.andesui.moneyamount.MoneyAmountUtils.ERROR
import com.mercadolibre.android.andesui.moneyamount.decimalstyle.AndesMoneyAmountDecimalsStyle
import com.mercadolibre.android.andesui.moneyamount.size.AndesMoneyAmountSize

internal data class AndesMoneyAmountConfiguration(
    val amountSize: Float,
    val iconSize: Float,
    val iconPadding: Float,
    val showIcon: Boolean,
    val currencyColor: AndesColor,
    val suffixColor: AndesColor,
    val isValidData: ERROR,
    val countryInfo: AndesCountryInfo,
    val currencyInfo: AndesCurrencyInfo,
    val showZerosDecimal: Boolean,
    val superScriptSize: Float,
    val suffix: SpannableStringBuilder?,
    val suffixAccessibility: String?,
    val suffixSize: Float,
    val suffixPadding: Float,
    val amountFormatted: SpannableStringBuilder
)

internal object AndesMoneyAmountConfigurationFactory {

    fun create(context: Context, andesMoneyAmountAttrs: AndesMoneyAmountAttrs): AndesMoneyAmountConfiguration {
        return with(andesMoneyAmountAttrs) {
            val countryInfo = AndesCurrencyHelper.getCountry(andesMoneyAmountAttrs.andesMoneyAmountCountry)
            val currencyInfo = AndesCurrencyHelper.getCurrency(andesMoneyAmountAttrs.andesMoneyAmountCurrency)
            val suffixSize = andesMoneyAmountSize.size.suffixSize(context)
            val suffixPadding = andesMoneyAmountSize.size.suffixPadding(context)
            val suffixColor = resolveTextColor(andesTextColor,  R.color.andes_text_color_secondary.toAndesColor())
            val superScriptSize = andesMoneyAmountSize.size.superScriptSize(context)
            val amount = andesMoneyAmountDecimalsStyle.style.getAmountStyled(
                andesMoneyAmount,
                countryInfo,
                currencyInfo,
                andesShowZerosDecimal,
                superScriptSize
            )
            AndesMoneyAmountConfiguration(
                amountSize = andesMoneyAmountSize.size.textSize(context),
                iconSize = andesMoneyAmountSize.size.iconSize(context),
                iconPadding = andesMoneyAmountSize.size.iconPadding(context),
                showIcon = andesShowIcon,
                currencyColor = resolveTextColor(andesTextColor, andesMoneyAmountType.type.getTextColor()),
                suffixColor = suffixColor,
                isValidData = resolveValidData(currencyInfo, andesMoneyAmountAttrs.andesMoneyAmountDecimalsStyle,
                    andesMoneyAmountAttrs.andesSuffix, andesMoneyAmountAttrs.andesMoneyAmountSize),
                countryInfo = countryInfo,
                currencyInfo = currencyInfo,
                suffix = andesSuffix,
                suffixAccessibility = andesSuffixAccessibility,
                suffixSize = suffixSize,
                suffixPadding = suffixPadding,
                showZerosDecimal = andesShowZerosDecimal,
                superScriptSize = superScriptSize,
                amountFormatted = MoneyAmountUtils.formatMoneyAmount(
                    context = context,
                    amount = amount,
                    type = andesMoneyAmountType,
                    suffix = andesSuffix,
                    suffixSize = suffixSize,
                    suffixPadding = suffixPadding,
                    suffixColor = suffixColor
                )
            )
        }
    }

    private fun resolveTextColor(overrideColor: AndesColor?, defaultColor: AndesColor): AndesColor {
        return overrideColor ?: defaultColor
    }

    private fun resolveValidData(currency: AndesCurrencyInfo, decimalsStyle: AndesMoneyAmountDecimalsStyle,
                                 suffix: SpannableStringBuilder?, size: AndesMoneyAmountSize): ERROR {
        if (decimalsStyle == AndesMoneyAmountDecimalsStyle.SUPERSCRIPT &&
            (size == AndesMoneyAmountSize.SIZE_12 || size == AndesMoneyAmountSize.SIZE_14)) {
            return ERROR.SIZE
        }
        if (currency.isCrypto && decimalsStyle != AndesMoneyAmountDecimalsStyle.NORMAL) {
            return ERROR.DECIMAL_FORMAT
        }
        if (size == AndesMoneyAmountSize.SIZE_12 && suffix != null) {
            return ERROR.SUFFIX_SIZE
        }
        return ERROR.NONE
    }

}
