package com.mercadolibre.android.andesui.checkbox.accessibility

import android.content.res.Resources
import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import androidx.annotation.VisibleForTesting
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.checkbox.AndesCheckbox
import com.mercadolibre.android.andesui.checkbox.status.AndesCheckboxStatus
import com.mercadolibre.android.andesui.checkbox.type.AndesCheckboxType

internal class AndesCheckboxAccessibilityDelegate(private val andesCheckbox: AndesCheckbox) :
    View.AccessibilityDelegate() {

    override fun onInitializeAccessibilityNodeInfo(host: View?, info: AccessibilityNodeInfo?) {
        super.onInitializeAccessibilityNodeInfo(host, info)
        val resources = andesCheckbox.resources
        addActionIfNeeded(info, resources)
        info?.contentDescription = generateContentDescriptionText(resources)
    }

    @VisibleForTesting
    internal fun addActionIfNeeded(info: AccessibilityNodeInfo?, resources: Resources) {
        if (andesCheckbox.type != AndesCheckboxType.DISABLED) {
            info?.addAction(generateActionForType(resources))
        }
    }

    private fun generateContentDescriptionText(resources: Resources): String = buildString {
        resolveErrorStatus(resources)?.let {
            append(it, COMMA_SEPARATOR)
        }
        append(resolveStatus(andesCheckbox.status, resources), COMMA_SEPARATOR)
        append(andesCheckbox.text.orEmpty())
    }

    private fun resolveErrorStatus(resources: Resources) =
        if (andesCheckbox.type == AndesCheckboxType.ERROR) {
            resources.getString(R.string.andes_checkbox_type_error)
        } else {
            null
        }

    private fun resolveStatus(status: AndesCheckboxStatus, resources: Resources): String {
        return when (status) {
            AndesCheckboxStatus.SELECTED -> resources.getString(R.string.andes_checkbox_status_selected)
            AndesCheckboxStatus.UNSELECTED -> resources.getString(R.string.andes_checkbox_status_unselected)
            AndesCheckboxStatus.UNDEFINED -> resources.getString(R.string.andes_checkbox_status_undefined)
        }
    }

    private fun generateActionForType(resources: Resources): AccessibilityNodeInfo.AccessibilityAction {
        return AccessibilityNodeInfo.AccessibilityAction(
            AccessibilityNodeInfo.ACTION_CLICK,
            getActionClickWord(resources)
        )
    }

    private fun getActionClickWord(resources: Resources): String = when (andesCheckbox.status) {
        AndesCheckboxStatus.SELECTED -> resources.getString(R.string.andes_checkbox_action_click_unselect)
        else -> resources.getString(R.string.andes_checkbox_action_click_select)
    }

    companion object {
        private const val COMMA_SEPARATOR = ", "
    }
}
