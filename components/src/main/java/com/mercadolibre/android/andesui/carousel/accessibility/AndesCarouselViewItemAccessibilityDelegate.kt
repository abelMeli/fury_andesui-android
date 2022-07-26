package com.mercadolibre.android.andesui.carousel.accessibility

import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat

/**
 * Delegate for every item in the inner recyclerview.
 */
internal class AndesCarouselViewItemAccessibilityDelegate(private val position: Int) : View.AccessibilityDelegate() {

    override fun onInitializeAccessibilityNodeInfo(host: View?, info: AccessibilityNodeInfo?) {
        super.onInitializeAccessibilityNodeInfo(host, info)
        info?.let { AccessibilityNodeInfoCompat.wrap(it) }?.apply {
            setCollectionItemInfo(
                AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(
                    position,
                    SINGLE_SPAN,
                    SINGLE_COLUMN_INDEX,
                    SINGLE_SPAN,
                    false,
                    isSelected
                )
            )
        }
    }

    companion object {
        private const val SINGLE_COLUMN_INDEX = 0
        private const val SINGLE_SPAN = 1
    }
}
