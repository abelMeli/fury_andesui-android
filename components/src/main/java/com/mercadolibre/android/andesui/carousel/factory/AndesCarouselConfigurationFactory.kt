package com.mercadolibre.android.andesui.carousel.factory

import android.content.Context
import com.mercadolibre.android.andesui.carousel.margin.AndesCarouselMarginInterface

internal data class AndesCarouselConfiguration(
    val margin: Int,
    val padding: Int,
    val center: Boolean
)

internal object AndesCarouselConfigurationFactory {

    fun create(context: Context, andesCarouselAttrs: AndesCarouselAttrs): AndesCarouselConfiguration {
        val margin = andesCarouselAttrs.andesCarouselMargin.margin
        return AndesCarouselConfiguration(
            margin = resolveItemMargin(margin, context),
            padding = resolveRecyclerPadding(margin, context),
            center = andesCarouselAttrs.andesCarouselCenter
        )
    }

    private fun resolveItemMargin(margin: AndesCarouselMarginInterface, context: Context) = margin.getMargin(context)
    private fun resolveRecyclerPadding(padding: AndesCarouselMarginInterface, context: Context) = padding.getPadding(context)
}
