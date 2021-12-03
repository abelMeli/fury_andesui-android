package com.mercadolibre.android.andesui.checkbox.accessibility

import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import androidx.annotation.VisibleForTesting
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.checkbox.AndesCheckbox
import com.mercadolibre.android.andesui.checkbox.status.AndesCheckboxStatus
import com.mercadolibre.android.andesui.checkbox.type.AndesCheckboxType
import com.mercadolibre.android.andesui.utils.createActionList

@Suppress("TooManyFunctions")
internal class AndesCheckboxAccessibilityDelegate(private val andesCheckbox: AndesCheckbox) :
    View.AccessibilityDelegate() {

    private val actionList by lazy {
        createActionList(
            andesCheckbox.context,
            andesCheckbox.bodyLinks,
            andesCheckbox.spannableText
        )
    }

    override fun onInitializeAccessibilityNodeInfo(host: View?, info: AccessibilityNodeInfo?) {
        super.onInitializeAccessibilityNodeInfo(host, info)
        val resources = andesCheckbox.resources
        addActionIfNeeded(info, resources)
        info?.contentDescription = generateContentDescriptionText(resources)
        addLinkActions(info)
    }

    /**
     * Add the click action when the component is not in DISABLED state.
     */
    @VisibleForTesting
    internal fun addActionIfNeeded(info: AccessibilityNodeInfo?, resources: Resources) {
        if (andesCheckbox.type != AndesCheckboxType.DISABLED) {
            info?.addAction(generateActionForType(resources))
        }
    }

    override fun performAccessibilityAction(host: View?, action: Int, args: Bundle?): Boolean {
        val listOfActionIds = actionList.map { it.id }
        when (action) {
            in listOfActionIds -> {
                andesCheckbox.bodyLinks?.listener?.invoke(listOfActionIds.indexOf(action))
                return true
            }
        }
        return super.performAccessibilityAction(host, action, args)
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

    /**
     * Adds one action per link present in the [andesCheckbox].
     */
    private fun addLinkActions(info: AccessibilityNodeInfo?) {
        actionList.forEach {
            info?.addAction(it)
        }
    }

    companion object {
        private const val COMMA_SEPARATOR = ", "
    }
}
