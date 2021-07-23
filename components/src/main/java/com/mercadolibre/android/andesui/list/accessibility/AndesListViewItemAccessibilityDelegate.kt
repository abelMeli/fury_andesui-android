package com.mercadolibre.android.andesui.list.accessibility

import android.content.res.Resources
import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.list.AndesListViewItem

/**
 * Class used to resolve [AndesListViewItem] accessibility features.
 */
internal class AndesListViewItemAccessibilityDelegate(private val andesListItem: AndesListViewItem) :
    View.AccessibilityDelegate() {

    companion object {
        private const val CONTENT_DESCRIPTION_SEPARATOR = ","
    }

    override fun onInitializeAccessibilityNodeInfo(host: View?, info: AccessibilityNodeInfo?) {
        super.onInitializeAccessibilityNodeInfo(host, info)
        info?.contentDescription = generateContentDescriptionText(host?.resources)
    }

    private fun generateContentDescriptionText(resources: Resources?) = buildString {
        if (andesListItem.itemSelected == true) {
            append(
                resources?.getString(R.string.andes_list_item_selected).orEmpty(),
                CONTENT_DESCRIPTION_SEPARATOR
            )
        }

        if (andesListItem.icon != null && andesListItem.iconContentDescription != null) {
            append(andesListItem.iconContentDescription, CONTENT_DESCRIPTION_SEPARATOR)
        }

        if (andesListItem.avatar != null && andesListItem.avatarContentDescription != null) {
            append(andesListItem.avatarContentDescription, CONTENT_DESCRIPTION_SEPARATOR)
        }

        append(andesListItem.title, CONTENT_DESCRIPTION_SEPARATOR)
        if (andesListItem.showSubtitle) {
            append(andesListItem.subtitle.orEmpty())
        }
    }
}
