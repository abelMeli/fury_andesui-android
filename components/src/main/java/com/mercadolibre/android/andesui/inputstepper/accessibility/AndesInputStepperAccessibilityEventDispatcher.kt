package com.mercadolibre.android.andesui.inputstepper.accessibility

import android.view.View
import com.mercadolibre.android.andesui.inputstepper.AndesInputStepper

/**
 * Contains accessibility talkback events for [AndesInputStepper]
 */
internal class AndesInputStepperAccessibilityEventDispatcher {

    /**
     * Notifies the changes of value to user
     * @param text The announcement text
     */
    internal fun notifyA11ySpinnerSetValue(view: View, text: String) {
        view.announceForAccessibility(text)
    }
}