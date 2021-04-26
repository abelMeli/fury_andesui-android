package com.mercadolibre.android.andesui.carousel.factory

import com.mercadolibre.android.andesui.R
import android.content.Context
import android.util.AttributeSet
import com.mercadolibre.android.andesui.carousel.margin.AndesCarouselMargin

internal data class AndesCarouselAttrs(
    val andesCarouselCenter: Boolean,
    val andesCarouselMargin: AndesCarouselMargin
)

/**
 * This object parse the attribute set and return an instance of AndesCarouselAttrs
 * to be used by AndesCarousel
 */
internal object AndesCarouselAttrParser {

    private const val ANDES_CAROUSEL_MARGIN_NONE = "100"
    private const val ANDES_CAROUSEL_MARGIN_DEFAULT = "101"
    private const val ANDES_CAROUSEL_MARGIN_XSMALL = "102"

    fun parse(context: Context, attr: AttributeSet?): AndesCarouselAttrs {

        val typedArray = context.obtainStyledAttributes(attr, R.styleable.AndesCarousel)

        val margin = when (typedArray.getString(R.styleable.AndesCarousel_andesCarouselMargin)) {
            ANDES_CAROUSEL_MARGIN_NONE -> AndesCarouselMargin.NONE
            ANDES_CAROUSEL_MARGIN_DEFAULT -> AndesCarouselMargin.DEFAULT
            ANDES_CAROUSEL_MARGIN_XSMALL -> AndesCarouselMargin.XSMALL
            else -> AndesCarouselMargin.DEFAULT
        }

        val center = typedArray.getBoolean(R.styleable.AndesCarousel_andesCarouselCenter, false)

        return AndesCarouselAttrs(
                andesCarouselCenter = center,
                andesCarouselMargin = margin
        ).also { typedArray.recycle() }
    }
}
