package com.mercadolibre.android.andesui.textfield.accessibility

import android.view.View
import com.mercadolibre.android.andesui.R

/**
 * class responsible for sending particular events to the talkback.
 */
internal class AndesTextfieldAccessibilityEventDispatcher {

    /**
     * method responsible for the announce emitted when the entered text reaches the
     * 80% threshold.
     */
    internal fun notifyA11yTextThresholdReached(
        view: View,
        maxCharsCount: Int,
        minCounterSize: Int = MIN_COUNTER_SIZE_FOR_A11Y_EVENT_THRESHOLD,
        text: String
    ) {
        if (maxCharsCount >= minCounterSize) {
            val threshold = MIN_PERCENTAGE_THRESHOLD * maxCharsCount / TOTAL_PERCENTAGE_TEXTFIELD
            val charsLeft = (maxCharsCount - threshold)

            if (text.length == threshold) {
                val eventText = view
                        .context
                        .resources
                        .getQuantityString(R.plurals.andes_textfield_chars_left, charsLeft, charsLeft)
                view.announceForAccessibility(eventText)
            }
        }
    }

    companion object {
        private const val MIN_COUNTER_SIZE_FOR_A11Y_EVENT_THRESHOLD = 20
        private const val MIN_PERCENTAGE_THRESHOLD = 80
        private const val TOTAL_PERCENTAGE_TEXTFIELD = 100
    }
}
