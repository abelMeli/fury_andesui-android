package com.mercadolibre.android.andesui.carousel.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * AndesCarouselInfiniteItemDecoration: override the itemDecoration to recyclerview which uses in AndesCarousel
 * when the carousel is set up as infinite.
 */
internal class AndesCarouselInfiniteItemDecoration(private val margin: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            left = margin
            right = margin
        }
    }
}
