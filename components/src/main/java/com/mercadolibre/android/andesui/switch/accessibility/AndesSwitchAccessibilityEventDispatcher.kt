package com.mercadolibre.android.andesui.switch.accessibility

import android.view.View
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.switch.status.AndesSwitchStatus

internal class AndesSwitchAccessibilityEventDispatcher {

    internal fun notifyA11yStatusChanged(view: View, status: AndesSwitchStatus) {
        val resources = view.context.resources
        val announce = if (status == AndesSwitchStatus.CHECKED) {
            resources.getString(R.string.andes_switch_status_checked)
        } else {
            resources.getString(R.string.andes_switch_status_unchecked)
        }
        view.announceForAccessibility(announce)
    }
}
