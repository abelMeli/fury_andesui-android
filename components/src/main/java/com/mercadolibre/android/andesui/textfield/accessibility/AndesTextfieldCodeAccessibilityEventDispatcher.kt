package com.mercadolibre.android.andesui.textfield.accessibility

import android.view.View
import com.mercadolibre.android.andesui.R

/**
 * class responsible for sending particular events to the talkback.
 */
internal class AndesTextfieldCodeAccessibilityEventDispatcher {

    /**
     * method responsible for the announce emitted when all needed values are entered.
     */
    internal fun notifyA11yTextCompleted(view: View, isFull: Boolean) {
        if (isFull) {
            val eventText = view.context
                    .getString(R.string.andes_textfield_field_completed)
            view.announceForAccessibility(eventText)
        }
    }
}
