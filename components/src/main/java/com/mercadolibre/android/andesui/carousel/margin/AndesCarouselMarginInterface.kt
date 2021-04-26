package com.mercadolibre.android.andesui.carousel.margin

import android.content.Context
import com.mercadolibre.android.andesui.R

private const val TEN_PERCENTAGE = 0.10
private const val ZERO_PERCENTAGE = 0.001

/**
 * Defines all types related properties that an [AndesCarousel] needs to be drawn properly.
 * Those properties change depending on the style of the andesCarousel.
 */
internal interface AndesCarouselMarginInterface {
    /**
     * Return dimen of margin to separate items in Carousel
     */
    fun getMargin(context: Context): Int

    /**
     * Returns the dimen of the horizontal padding of the recycler
     */
    fun getPadding(context: Context): Int
}

internal object AndesCarouselMarginNone : AndesCarouselMarginInterface {
    override fun getMargin(context: Context) = context.resources.getDimension(R.dimen.andes_carousel_padding_none).toInt()
    override fun getPadding(context: Context): Int = (context.resources.displayMetrics.widthPixels * ZERO_PERCENTAGE).toInt()
}

internal object AndesCarouselMarginDefault : AndesCarouselMarginInterface {
    override fun getMargin(context: Context) = context.resources.getDimension(R.dimen.andes_carousel_padding_small).toInt()
    override fun getPadding(context: Context): Int = (context.resources.displayMetrics.widthPixels * TEN_PERCENTAGE).toInt()
}

internal object AndesCarouselMarginXSmall : AndesCarouselMarginInterface {
    override fun getMargin(context: Context) = context.resources.getDimension(R.dimen.andes_carousel_padding_xsmall).toInt()
    override fun getPadding(context: Context): Int = context.resources.getDimension(R.dimen.andes_carousel_recylcer_padding_xsmall).toInt()
}
