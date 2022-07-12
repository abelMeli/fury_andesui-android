package com.mercadolibre.android.andesui.utils.pagetransformer

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

internal class AndesFadeOutTransformer : ViewPager2.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        page.alpha = if (position < POSITION_LEFT) {
            // This page is way off-screen to the left.
            FADE_OUT_ALPHA
        } else {
            // This page is way off-screen to the right.
            FADE_IN_ALPHA - abs(position)
        }
    }

    companion object {
        private const val POSITION_LEFT = -1f
        private const val FADE_OUT_ALPHA = 0f
        private const val FADE_IN_ALPHA = 1f
    }
}
