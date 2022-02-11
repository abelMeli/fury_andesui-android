package com.mercadolibre.android.andesui.carousel.factory

import android.content.Context
import android.util.AttributeSet
import com.mercadolibre.android.andesui.R
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
    private const val ANDES_CAROUSEL_MARGIN_SMALL = "103"
    private const val ANDES_CAROUSEL_MARGIN_MEDIUM = "104"
    private const val ANDES_CAROUSEL_MARGIN_LARGE = "105"

    fun parse(context: Context, attr: AttributeSet?): AndesCarouselAttrs {

        val typedArray = context.obtainStyledAttributes(attr, R.styleable.AndesCarousel)

        val margin = when (typedArray.getString(R.styleable.AndesCarousel_andesCarouselMargin)) {
            ANDES_CAROUSEL_MARGIN_NONE -> AndesCarouselMargin.NONE
            ANDES_CAROUSEL_MARGIN_DEFAULT -> AndesCarouselMargin.DEFAULT
            ANDES_CAROUSEL_MARGIN_XSMALL -> AndesCarouselMargin.XSMALL
            ANDES_CAROUSEL_MARGIN_SMALL -> AndesCarouselMargin.SMALL
            ANDES_CAROUSEL_MARGIN_MEDIUM -> AndesCarouselMargin.MEDIUM
            ANDES_CAROUSEL_MARGIN_LARGE -> AndesCarouselMargin.LARGE
            else -> AndesCarouselMargin.DEFAULT
        }

        val center = typedArray.getBoolean(R.styleable.AndesCarousel_andesCarouselCenter, false)

        return AndesCarouselAttrs(
                andesCarouselCenter = center,
                andesCarouselMargin = margin
        ).also { typedArray.recycle() }
    }
}
