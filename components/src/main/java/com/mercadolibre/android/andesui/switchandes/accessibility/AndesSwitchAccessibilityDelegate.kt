package com.mercadolibre.android.andesui.switchandes.accessibility

import android.content.res.Resources
import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import androidx.annotation.VisibleForTesting
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.switchandes.AndesSwitch
import com.mercadolibre.android.andesui.switchandes.status.AndesSwitchStatus
import com.mercadolibre.android.andesui.switchandes.type.AndesSwitchType

internal class AndesSwitchAccessibilityDelegate(private val andesSwitch: AndesSwitch) : View.AccessibilityDelegate() {

    override fun onInitializeAccessibilityNodeInfo(host: View?, info: AccessibilityNodeInfo?) {
        super.onInitializeAccessibilityNodeInfo(host, info)
        addActionIfNeeded(info, andesSwitch)
        info?.text = generateContentDescriptionText(andesSwitch)
    }

    @VisibleForTesting
    internal fun addActionIfNeeded(info: AccessibilityNodeInfo?, andesSwitch: AndesSwitch) {
        if (andesSwitch.type == AndesSwitchType.ENABLED) {
            info?.addAction(generateActionForType(andesSwitch))
        }
    }

    private fun generateContentDescriptionText(andesSwitch: AndesSwitch): String {
        val resources = andesSwitch.context.resources
        val text = andesSwitch.text.orEmpty()
        val status = resolveStatus(andesSwitch.status, resources)
        return "$text. $status"
    }

    private fun generateActionForType(andesSwitch: AndesSwitch): AccessibilityNodeInfo.AccessibilityAction {
        return AccessibilityNodeInfo.AccessibilityAction(AccessibilityNodeInfo.ACTION_CLICK,
                getSwitchStatusWord(andesSwitch))
    }

    private fun resolveStatus(status: AndesSwitchStatus, resources: Resources): String {
        return when (status) {
            AndesSwitchStatus.CHECKED -> {
                resources.getString(R.string.andes_switch_status_checked)
            }
            AndesSwitchStatus.UNCHECKED -> {
                resources.getString(R.string.andes_switch_status_unchecked)
            }
        }
    }

    private fun getSwitchStatusWord(andesSwitch: AndesSwitch): String {
        return when (andesSwitch.status) {
            AndesSwitchStatus.CHECKED -> {
                andesSwitch.context.resources.getString(R.string.andes_switch_action_uncheck)
            }
            AndesSwitchStatus.UNCHECKED -> {
                andesSwitch.context.resources.getString(R.string.andes_switch_action_check)
            }
        }
    }
}
