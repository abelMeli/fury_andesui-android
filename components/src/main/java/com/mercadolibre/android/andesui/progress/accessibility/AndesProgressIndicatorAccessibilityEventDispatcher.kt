package com.mercadolibre.android.andesui.progress.accessibility

import android.view.View
import com.mercadolibre.android.andesui.R

/**
 * class responsible for sending particular events to the talkback.
 */
internal class AndesProgressIndicatorAccessibilityEventDispatcher {

    /**
     * method responsible for the announce emitted when all needed values are entered.
     */
    internal fun notifyA11ySpinnerIsLoading(view: View, isLoading: Boolean) {
        if (isLoading) {
            val eventText = view.context
                    .getString(R.string.andes_progressindicator_is_loading)
            view.announceForAccessibility(eventText)
        }
    }
}
