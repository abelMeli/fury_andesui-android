package com.mercadolibre.android.andesui.slider.accessibility

import android.view.View
import com.mercadolibre.android.andesui.R

/**
 * class responsible for sending particular events to the talkback.
 */
internal class AndesSliderAccessibilityEventDispatcher {

    /**
     * method responsible for the announce emitted when slider value changed.
     */
    internal fun notifyA11yChangedValue(view: View, value: Float, a11ySuffix: String) {
        view.announceForAccessibility(
            view.resources.getString(
                R.string.andes_slider_selected_value,
                value.toString(),
                a11ySuffix
            )
        )
    }
}
