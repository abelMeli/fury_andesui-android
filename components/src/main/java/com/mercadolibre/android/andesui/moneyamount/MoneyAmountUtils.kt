package com.mercadolibre.android.andesui.moneyamount

import android.content.Context
import android.graphics.Rect
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.style.AbsoluteSizeSpan
import android.text.style.MetricAffectingSpan
import com.mercadolibre.android.andesui.color.AndesColor
import com.mercadolibre.android.andesui.currency.AndesCountryInfo
import com.mercadolibre.android.andesui.moneyamount.type.AndesMoneyAmountType
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

internal object MoneyAmountUtils {

    fun formatAmount(
        amount: Double,
        decimalPlaces: Int,
        currencySymbol: String,
        country: AndesCountryInfo
    ) = "$currencySymbol ${getLocatedDecimalFormat(decimalPlaces, getSeparators(country)).format(amount)}"

    internal fun getLocatedDecimalFormat(decimalPlaces: Int, dfs: DecimalFormatSymbols): DecimalFormat =
        DecimalFormat().apply {
            decimalFormatSymbols = dfs
            minimumFractionDigits = decimalPlaces
            maximumFractionDigits = decimalPlaces
        }

    private fun getSeparators(country: AndesCountryInfo): DecimalFormatSymbols =
        DecimalFormatSymbols().apply {
            decimalSeparator = country.decimalSeparator
            groupingSeparator = country.thousandsSeparator
        }

    internal fun formatMoneyAmount(
        context: Context,
        amount: SpannableStringBuilder,
        type: AndesMoneyAmountType,
        suffix: SpannableStringBuilder?,
        suffixSize: Float,
        suffixPadding: Float,
        suffixColor: AndesColor
    ): SpannableStringBuilder {
        val moneyAmount = SpannableStringBuilder(type.type.getSign()).append(amount)
        suffix?.let {
            it.apply {
                setSpan(
                    PaddingCustomSpan(suffixPadding, suffixColor.colorInt(context)),
                    0,
                    suffix.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                setSpan(
                    AbsoluteSizeSpan(suffixSize.toInt(), false),
                    0,
                    suffix.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                moneyAmount.append(it)
            }
        }

        moneyAmount.apply {
            type.type.setStrikeThrough(this)
        }
        return moneyAmount
    }

    /**
     * Draw the super script in the correct position and size
     */
    internal class CustomRelativeSizeSpan(val size: Float) : MetricAffectingSpan() {

        override fun updateDrawState(ds: TextPaint) {
            updateAnyState(ds)
        }

        override fun updateMeasureState(ds: TextPaint) {
            updateAnyState(ds)
        }

        private fun updateAnyState(ds: TextPaint) {
            val bounds = Rect()
            ds.getTextBounds("1A", 0, 2, bounds)
            var shift: Int = bounds.top - bounds.bottom
            ds.textSize = size
            ds.getTextBounds("1A", 0, 2, bounds)
            shift += bounds.bottom - bounds.top
            ds.baselineShift += shift
        }
    }

    internal enum class ERROR {
        SIZE, SUFFIX_SIZE, DECIMAL_FORMAT, NONE
    }
}
