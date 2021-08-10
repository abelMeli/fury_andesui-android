package com.mercadolibre.android.andesui.checkbox.accessibility

import android.view.View
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.checkbox.status.AndesCheckboxStatus

internal class AndesCheckboxAccessibilityEventDispatcher {

    internal fun notifyA11yStatusChanged(view: View, status: AndesCheckboxStatus) {
        val resources = view.context.resources
        val announce = if (status == AndesCheckboxStatus.SELECTED) {
            resources.getString(R.string.andes_checkbox_status_selected)
        } else {
            resources.getString(R.string.andes_checkbox_status_unselected)
        }
        view.announceForAccessibility(announce)
    }
}
