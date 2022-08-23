package com.mercadolibre.android.andesui.list.accessibility

import android.content.res.Resources
import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import androidx.annotation.VisibleForTesting
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.list.AndesListViewItem
import com.mercadolibre.android.andesui.list.AndesListViewItemCheckBox
import com.mercadolibre.android.andesui.list.AndesListViewItemRadioButton

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
        when (andesListItem) {
            is AndesListViewItemCheckBox,
            is AndesListViewItemRadioButton -> {
                info?.contentDescription = generateContentDescriptionText()
                info?.isCheckable = true
                info?.isChecked = andesListItem.itemSelected == true
            }
            else -> {
                info?.contentDescription = generateContentDescriptionText()
                info?.isSelected = andesListItem.itemSelected == true
            }
        }
    }

    private fun generateContentDescriptionText() = buildString {
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
