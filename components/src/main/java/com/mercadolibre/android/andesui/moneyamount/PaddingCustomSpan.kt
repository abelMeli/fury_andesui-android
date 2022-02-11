package com.mercadolibre.android.andesui.moneyamount

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.FontMetricsInt
import android.text.style.ReplacementSpan
import androidx.annotation.ColorInt
import kotlin.math.roundToInt

class PaddingCustomSpan(private val leftPadding: Float, @ColorInt private val color: Int) : ReplacementSpan() {

    override fun draw(
        canvas: Canvas,
        text: CharSequence,
        start: Int,
        end: Int,
        x: Float,
        top: Int,
        y: Int,
        bottom: Int,
        paint: Paint
    ) {
        paint.color = color
        val yPos = (canvas.height / 2 - (paint.descent() + paint.ascent()))
        canvas.drawText(text, start, end, x + leftPadding, yPos, paint)
    }

    override fun getSize(paint: Paint, text: CharSequence?, start: Int, end: Int, fm: FontMetricsInt?): Int {
        return (paint.measureText(text, start, end) + leftPadding).roundToInt()
    }

}
