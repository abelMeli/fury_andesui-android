package com.mercadolibre.android.andesui.modal.common.contentvariation.interfaces

import android.content.Context
import android.view.View

internal interface AndesModalContentVariationInterface {
    val bannerVisibility: Int
    val thumbnailVisibility: Int

    fun getHeight(context: Context) = FULL_SIZE
    fun getWidth(context: Context) = FULL_SIZE
    fun getMarginTop(context: Context) = NO_MARGIN
    fun getVerticalBias() = DEFAULT_VALUE_BIAS
    fun getTitleVisibility() = View.VISIBLE
    fun getSubtitleTextAlignment() = View.TEXT_ALIGNMENT_TEXT_START

    companion object {
        private const val FULL_SIZE = 0
        private const val NO_MARGIN = 0
        const val VERTICAL_TOP_VALUE_BIAS = 0f
        const val DEFAULT_VALUE_BIAS = 0.5f
    }
}
