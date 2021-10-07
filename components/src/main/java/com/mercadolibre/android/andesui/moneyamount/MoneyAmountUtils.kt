package com.mercadolibre.android.andesui.moneyamount

import android.graphics.Rect
import android.text.TextPaint
import android.text.style.MetricAffectingSpan
import com.mercadolibre.android.andesui.currency.AndesCountryInfo
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

internal object MoneyAmountUtils {

    fun formatAmount(
        amount: Double,
        decimalPlaces: Int,
        currencySymbol: String,
        country: AndesCountryInfo
    ) = "$currencySymbol ${getLocatedDecimalFormat(decimalPlaces, a(country)).format(amount)}"

    internal fun getLocatedDecimalFormat(decimalPlaces: Int, dfs: DecimalFormatSymbols): DecimalFormat =
        DecimalFormat().apply {
            decimalFormatSymbols = dfs
            minimumFractionDigits = decimalPlaces
            maximumFractionDigits = decimalPlaces
        }

    private fun a(country: AndesCountryInfo): DecimalFormatSymbols =
        DecimalFormatSymbols().apply {
            decimalSeparator = country.decimalSeparator
            groupingSeparator = country.thousandsSeparator
        }

    /**
     * Draw the super script in the correct position and size
     */
    class CustomRelativeSizeSpan(private val sizeChange: Float) : MetricAffectingSpan() {

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
            ds.textSize = sizeChange
            ds.getTextBounds("1A", 0, 2, bounds)
            shift += bounds.bottom - bounds.top
            ds.baselineShift += shift
        }
    }
}
