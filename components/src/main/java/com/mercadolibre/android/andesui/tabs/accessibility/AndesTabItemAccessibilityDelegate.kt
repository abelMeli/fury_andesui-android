package com.mercadolibre.android.andesui.tabs.accessibility

import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityActionCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat.CollectionItemInfoCompat
import com.google.android.material.tabs.TabLayout
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.tabs.AndesTabItem

/**
 * class responsible for generating the component description for the andesTabItem, according
 * to the available parameters (position, selected, title)
 */
internal class AndesTabItemAccessibilityDelegate(
    private val tab: TabLayout.Tab,
    private val item: AndesTabItem
) : View.AccessibilityDelegate() {

    companion object {
        private const val SINGLE_ROW_INDEX = 0
        private const val SINGLE_SPAN = 1
    }

    /**
     * method responsible for sending the component description to the Talkback service.
     */
    override fun onInitializeAccessibilityNodeInfo(host: View?, info: AccessibilityNodeInfo?) {
        super.onInitializeAccessibilityNodeInfo(host, info)
        info?.let { AccessibilityNodeInfoCompat.wrap(it) }?.apply {
            setCollectionItemInfo(
                CollectionItemInfoCompat.obtain(
                    SINGLE_ROW_INDEX,
                    SINGLE_SPAN,
                    tab.position,
                    SINGLE_SPAN,
                    false,
                    isSelected
                )
            )
            if (isSelected) {
                isClickable = false
                removeAction(AccessibilityActionCompat.ACTION_CLICK)
            }
            roleDescription = host?.resources?.getString(R.string.andes_tab_item_role_description)
            contentDescription = item.title
        }
    }
}
