package com.mercadolibre.android.andesui.shadows

import android.graphics.drawable.GradientDrawable
import androidx.annotation.ColorInt
import org.robolectric.annotation.Implementation
import org.robolectric.annotation.Implements
import org.robolectric.annotation.RealObject
import org.robolectric.shadow.api.Shadow
import org.robolectric.shadows.ShadowGradientDrawable

@Implements(GradientDrawable::class)
class AndesShadowGradientDrawable : ShadowGradientDrawable() {

    private var stroke: Int = 0

    var lastSetStrokeColor = stroke
        get() = stroke
        private set

    @RealObject
    private val realGradientDrawable: GradientDrawable? = null

    @Implementation
    fun setStroke(width: Int, @ColorInt color: Int) {
        stroke = color
        Shadow.directlyOn(realGradientDrawable, GradientDrawable::class.java).setStroke(width, color)
    }
}