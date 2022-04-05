package com.mercadolibre.android.andesui.amountfield.utils

import android.content.Context
import android.graphics.Rect
import android.text.TextPaint
import android.widget.EditText
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.amountfield.size.AndesAmountFieldSize
import com.mercadolibre.android.andesui.typeface.getFontOrDefault

internal object TextDimensionUtils {

    /**
     * Allows to measure the width of a string entered in the amountField according to the selected [size]
     */
    fun getStringWidth(context: Context, size: AndesAmountFieldSize, stringToMeasure: String) =
        getTextPaint(context, size.size.amountSize(context)).measureStringWidth(stringToMeasure)

    /**
     * Returns the width of the currency symbol + suffix + margins between currency, editable field and suffix
     */
    fun getNonEditableViewsTotalWidth(
        context: Context,
        currencySymbol: String,
        suffixText: String,
        size: AndesAmountFieldSize
    ): Int {
        val currencyWidth =
            getTextPaint(context, size.size.currencySize(context)).measureStringWidth(currencySymbol)
        val suffixSize =
            getTextPaint(context, size.size.suffixSize(context)).measureStringWidth(suffixText)
        val marginsSize = size.size.horizontalMargin(context) * 2

        return currencyWidth + suffixSize + marginsSize
    }

    private fun getTextPaint(context: Context, textSize: Float): TextPaint {
        return EditText(context)
            .apply {
                typeface = context.getFontOrDefault(R.font.andes_font_regular)
            }
            .paint
            .apply {
                this.textSize = textSize
            }
    }
}

private fun TextPaint.measureStringWidth(stringToMeasure: String): Int {
    val rect = Rect()
    getTextBounds(stringToMeasure, 0, stringToMeasure.length, rect)

    return rect.width()
}
