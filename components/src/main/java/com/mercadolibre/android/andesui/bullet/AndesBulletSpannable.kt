package com.mercadolibre.android.andesui.bullet

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.text.Layout
import android.text.Spanned
import android.text.style.LeadingMarginSpan
import androidx.annotation.ColorInt
import androidx.annotation.Px

class AndesBulletSpannable(
    @Px private val gapWidth: Int,
    @ColorInt private val color: Int,
    private val radius: Int
) : LeadingMarginSpan {
    private val bulletPath by lazy {
        val path = Path()
        path.addCircle(0.0f, 0.0f, radius.toFloat(), Path.Direction.CW)
        return@lazy path
    }

    override fun getLeadingMargin(first: Boolean) = 2 * radius + 2 * gapWidth

    override fun drawLeadingMargin(
        canvas: Canvas,
        paint: Paint,
        x: Int,
        dir: Int,
        top: Int,
        baseline: Int,
        bottom: Int,
        text: CharSequence,
        start: Int,
        end: Int,
        first: Boolean,
        layout: Layout?
    ) {
        if ((text as? Spanned)?.getSpanStart(this) == start) {
            val style = paint.style
            val oldColor = paint.color
            paint.color = color
            paint.style = Paint.Style.FILL
            val y = (top + bottom) / 2f
            if (canvas.isHardwareAccelerated) {
                canvas.save()
                canvas.translate((gapWidth + x + dir * radius).toFloat(), y)
                canvas.drawPath(bulletPath, paint)
                canvas.restore()
            } else {
                canvas.drawCircle((gapWidth + x + dir * radius).toFloat(), y, radius.toFloat(), paint)
            }
            paint.color = oldColor
            paint.style = style
        }
    }
}
