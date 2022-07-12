package com.mercadolibre.android.andesui.modal.card.corners

import android.graphics.Outline
import android.util.TypedValue
import android.view.View
import android.view.ViewOutlineProvider

internal interface AndesModalCornersInterface {
    fun getOutlineProvider(): ViewOutlineProvider
}

internal object AndesModalCornersAllCorners : AndesModalCornersInterface {

    private const val CORNER_RADIUS = 6f
    private const val LEFT = 0
    private const val TOP = 0

    override fun getOutlineProvider(): ViewOutlineProvider {
        return object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                val cornerRadius = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    CORNER_RADIUS,
                    view.context.resources.displayMetrics
                )
                outline.setRoundRect(LEFT, TOP, view.width, view.height, cornerRadius)
            }
        }
    }
}

internal object AndesModalCornersTopCorners : AndesModalCornersInterface {

    private const val CORNER_RADIUS = 6f
    private const val LEFT = 0
    private const val TOP = 0

    override fun getOutlineProvider(): ViewOutlineProvider {
        return object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                val cornerRadius = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    CORNER_RADIUS,
                    view.context.resources.displayMetrics
                )
                outline.setRoundRect(
                    LEFT,
                    TOP,
                    view.width,
                    view.height + cornerRadius.toInt(),
                    cornerRadius
                )
            }
        }
    }
}
