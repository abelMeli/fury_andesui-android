package com.mercadolibre.android.andesui.slider.accessibility

import android.content.res.Resources
import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.slider.AndesSlider
import com.mercadolibre.android.andesui.slider.state.AndesSliderState

/**
 * class responsible for generating the content description for the andeSlider, according
 * to the available parameters (state, text, value, min, max, others)
 */
internal class AndesSliderAccessibilityDelegate(private val andesSlider: AndesSlider) :
    View.AccessibilityDelegate() {

    /**
     * method responsible for sending the component description to the Talkback service.
     */
    override fun onInitializeAccessibilityNodeInfo(host: View?, info: AccessibilityNodeInfo?) {
        super.onInitializeAccessibilityNodeInfo(host, info)
        info?.contentDescription = generateContentDescriptionText(andesSlider, andesSlider.resources)
        info?.isEnabled = andesSlider.state == AndesSliderState.IDLE
    }

    /**
     * method responsible for generating the string needed as contentDescription.
     */
    private fun generateContentDescriptionText(andesSlider: AndesSlider, resources: Resources): CharSequence {
        // Note: Casted float to string value in order to show only needed decimal zero numbers.
        val sliderContent = resources.getString(
            R.string.andes_slider_content_description,
            andesSlider.min.toString(),
            andesSlider.max.toString(),
            andesSlider.accessibilityContentSuffix
        )
        val label = andesSlider.text ?: EMPTY_STRING
        val sliderValue = resources.getString(
            R.string.andes_slider_selected_value,
            andesSlider.value.toString(),
            andesSlider.accessibilityContentSuffix
        )
        return "$label, $sliderContent. $sliderValue"
    }

    companion object {
        private const val EMPTY_STRING = ""
    }
}
