package com.mercadolibre.android.andesui.carousel.factory

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.mercadolibre.android.andesui.carousel.margin.AndesCarouselMarginInterface
import com.mercadolibre.android.andesui.carousel.utils.AndesCarouselFiniteItemDecoration
import com.mercadolibre.android.andesui.carousel.utils.AndesCarouselInfiniteItemDecoration

internal data class AndesCarouselConfiguration(
    val padding: Int,
    val center: Boolean,
    val itemDecoration: RecyclerView.ItemDecoration,
    val infinite: Boolean,
    val autoplay: Boolean,
    val autoplaySpeed: Long,
    val title: String?,
    val paginator: Boolean
)

internal object AndesCarouselConfigurationFactory {
    fun create(context: Context, andesCarouselAttrs: AndesCarouselAttrs): AndesCarouselConfiguration {
        val attrMargin = andesCarouselAttrs.andesCarouselMargin.margin
        val margin = resolveItemMargin(attrMargin, context)
        val itemDecoration = resolveItemDecoration(margin, andesCarouselAttrs.andesCarouselInfinite)
        return AndesCarouselConfiguration(
            padding = resolveRecyclerPadding(attrMargin, context),
            center = andesCarouselAttrs.andesCarouselCenter,
            itemDecoration = itemDecoration,
            infinite = andesCarouselAttrs.andesCarouselInfinite,
            autoplay = andesCarouselAttrs.andesCarouselAutoplay,
            autoplaySpeed = andesCarouselAttrs.andesCarouselAutoplaySpeed,
            title = andesCarouselAttrs.andesCarouselTitle,
            paginator = andesCarouselAttrs.andesCarouselPaginator
        )
    }

    private fun resolveItemMargin(margin: AndesCarouselMarginInterface, context: Context) = margin.getMargin(context)
    private fun resolveRecyclerPadding(padding: AndesCarouselMarginInterface, context: Context) = padding.getPadding(context)
    private fun resolveItemDecoration(margin: Int, isInfinite: Boolean) =
        AndesCarouselInfiniteItemDecoration(margin)
            .takeIf { isInfinite } ?: AndesCarouselFiniteItemDecoration(margin)
}
