package com.mercadolibre.android.andesui.pageviewer.factory

import android.content.Context
import android.util.AttributeSet
import com.mercadolibre.android.andesui.R

internal data class AndesViewPagerAttrs(
    val andesViewPagerShouldMeasure: Boolean
)

internal object AndesViewPagerAttrsParser {
    fun parse(context: Context, attrs: AttributeSet?): AndesViewPagerAttrs {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.AndesViewPager)

        val shouldMeasure = typedArray.getBoolean(R.styleable.AndesViewPager_andesViewPagerShouldMeasure, false)

        return AndesViewPagerAttrs(shouldMeasure).also { typedArray.recycle() }
    }
}
