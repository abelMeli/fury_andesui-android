package com.mercadolibre.android.andesui.linearprogress.accessibility

import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.linearprogress.AndesLinearProgressIndicatorDeterminate

class AndesLinearProgressIndicatorAccessibilityDelegate(private val component: AndesLinearProgressIndicatorDeterminate) :
    View.AccessibilityDelegate() {

    override fun onInitializeAccessibilityNodeInfo(host: View?, info: AccessibilityNodeInfo?) {
        super.onInitializeAccessibilityNodeInfo(host, info)
        info?.contentDescription = generateContentDescriptionText(component)
    }

    private fun generateContentDescriptionText(component: AndesLinearProgressIndicatorDeterminate): String {
        val currentStep = component.currentStep
        val totalSteps = component.numberOfSteps
        val textStep = component.resources.getString(R.string.andes_linear_progress_step)
        val textOf = component.resources.getString(R.string.andes_linear_progress_of)
        return "$textStep $currentStep $textOf $totalSteps"
    }
}
