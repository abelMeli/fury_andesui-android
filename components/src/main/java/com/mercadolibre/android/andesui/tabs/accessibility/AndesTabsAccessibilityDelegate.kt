package com.mercadolibre.android.andesui.tabs.accessibility

import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import com.google.android.material.tabs.TabLayout

/**
 * class responsible for generating the component description for the andesTabs, according
 * to the available parameters (selectedPostion, size)
 */
internal class AndesTabsAccessibilityDelegate(private val tabLayout: TabLayout) :
    View.AccessibilityDelegate() {

    companion object {
        private const val SINGLE_ROW_COUNT = 1
    }

    /**
     * method responsible for sending the component description to the Talkback service.
     */
    override fun onInitializeAccessibilityNodeInfo(host: View?, info: AccessibilityNodeInfo?) {
        super.onInitializeAccessibilityNodeInfo(host, info)
        info?.collectionInfo = generateTabLayoutContentAnnouncement()
    }

    private fun generateTabLayoutContentAnnouncement(): AccessibilityNodeInfo.CollectionInfo {
        return AccessibilityNodeInfo.CollectionInfo.obtain(
            SINGLE_ROW_COUNT,
            tabLayout.tabCount,
            false,
            AccessibilityNodeInfo.CollectionInfo.SELECTION_MODE_SINGLE
        )
    }
}
