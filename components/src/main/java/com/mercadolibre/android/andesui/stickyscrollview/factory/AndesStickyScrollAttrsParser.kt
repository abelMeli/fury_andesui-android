package com.mercadolibre.android.andesui.stickyscrollview.factory

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.IdRes
import com.mercadolibre.android.andesui.R

internal data class AndesStickyScrollAttrs(
    @IdRes val headerRef: Int
)

internal object AndesStickyScrollAttrsParser {
    fun parse(context: Context, attr: AttributeSet?): AndesStickyScrollAttrs {
        val typedArray = context.obtainStyledAttributes(attr, R.styleable.AndesStickyScroll)

        val headerViewId = typedArray.getResourceId(R.styleable.AndesStickyScroll_andesStickyHeader, View.NO_ID)

        return AndesStickyScrollAttrs(headerViewId).also {
            typedArray.recycle()
        }
    }
}
