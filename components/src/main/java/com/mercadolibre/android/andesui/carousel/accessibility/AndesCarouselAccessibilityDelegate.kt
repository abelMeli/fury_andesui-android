package com.mercadolibre.android.andesui.carousel.accessibility

import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate

/**
 * class responsible for generating the component description for the carousel, according
 * to the size of the list.
 */
internal class AndesCarouselAccessibilityDelegate(recyclerView: RecyclerView, private val size: () -> Int) :
    RecyclerViewAccessibilityDelegate(recyclerView) {

    /**
     * method responsible for sending the component description to the Talkback service.
     */
    override fun onInitializeAccessibilityNodeInfo(host: View?, info: AccessibilityNodeInfoCompat?) {
        super.onInitializeAccessibilityNodeInfo(host, info)
        info?.setCollectionInfo(generateTabLayoutContentAnnouncement())
    }

    private fun generateTabLayoutContentAnnouncement(): AccessibilityNodeInfoCompat.CollectionInfoCompat? {
        return AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(
            size(),
            SINGLE_ROW_COUNT,
            false,
            AccessibilityNodeInfo.CollectionInfo.SELECTION_MODE_SINGLE
        )
    }

    companion object {
        private const val SINGLE_ROW_COUNT = 1
    }
}
