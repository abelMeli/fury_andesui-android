package com.mercadolibre.android.andesui.utils

import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

object AmountUtils {
    private const val INITIAL_PATTERN = "#,###"
    private const val MASK_CHAR = '#'

    /**
     * Returns a formatter that can be used to parse different type of number representations to
     * a valid amount format.
     * Example:
     *     val formatter = getAmountFormatter(numberOfDecimals = 2, decimalSeparator = ',')
     *     val stringToFormat = "1234567.89"
     *     val formattedString = formatter.format(stringToFormat)
     *     println(formattedString) // output: "1.234.567,89"
     */
    fun getAmountFormatter(numberOfDecimals: Int, decimalSeparator: Char): DecimalFormat {
        return createFormatter(numberOfDecimals, decimalSeparator)
    }

    private fun createFormatter(numberOfDecimals: Int, decimalSeparator: Char): DecimalFormat {
        val pattern = createPattern(numberOfDecimals)
        val decimalLocale = if (decimalSeparator.isDot()) {
            Locale.ENGLISH
        } else {
            Locale.ITALIAN
        }
        return DecimalFormat(pattern, DecimalFormatSymbols(decimalLocale)).apply {
            maximumFractionDigits = numberOfDecimals
            minimumFractionDigits = numberOfDecimals
            roundingMode = RoundingMode.DOWN
        }
    }

    private fun createPattern(numberOfDecimals: Int): String {
        var pattern: CharSequence = INITIAL_PATTERN

        if (numberOfDecimals == 0) {
            return pattern.toString()
        } else {
            pattern = pattern.append(DOT)
            (1..numberOfDecimals).forEach { _ ->
                pattern.append(MASK_CHAR)
            }
        }
        return pattern.toString()
    }
}
