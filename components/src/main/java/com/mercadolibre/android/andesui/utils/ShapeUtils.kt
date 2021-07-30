package com.mercadolibre.android.andesui.utils

import android.graphics.drawable.GradientDrawable

/**
 * the default radius is zero
 */
@Suppress("LongParameterList")
fun buildRectangleShape(
    color: Int,
    radius: Float = 0F,
    topLeftRadius: Float = 0F,
    topRightRadius: Float = 0F,
    bottomLeftRadius: Float = 0F,
    bottomRightRadius: Float = 0F
): GradientDrawable {
    val shape = GradientDrawable()
    shape.shape = GradientDrawable.RECTANGLE
    when {
        topLeftRadius > 0F || topRightRadius > 0F || bottomLeftRadius > 0F || bottomRightRadius > 0F -> {
            (shape.mutate() as GradientDrawable).cornerRadii =
                floatArrayOf(
                    topLeftRadius,
                    topLeftRadius,
                    topRightRadius,
                    topRightRadius,
                    bottomRightRadius,
                    bottomRightRadius,
                    bottomLeftRadius,
                    bottomLeftRadius
                )
        }
        else -> (shape.mutate() as GradientDrawable).cornerRadius = radius
    }
    shape.setColor(color)
    return shape
}
