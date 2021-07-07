package com.mercadolibre.android.andesui.button.accessibility

import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.button.AndesButton

/**
 * class responsible for generating the content description for the andesButton, according
 * to the available parameters (text, isLoading)
 */
internal class AndesButtonAccessibilityDelegate(private val andesButton: AndesButton) : View.AccessibilityDelegate() {

    /**
     * method responsible for sending the component description to the Talkback service.
     */
    override fun onInitializeAccessibilityNodeInfo(host: View?, info: AccessibilityNodeInfo?) {
        super.onInitializeAccessibilityNodeInfo(host, info)
        info?.contentDescription = generateContentDescriptionText(andesButton)
    }

    /**
     * method responsible for generating the string needed as contentDescription.
     *
     * The returned string varies according to the state and the presence/absence of the
     * text and the value of the isLoading parameter.
     */
    private fun generateContentDescriptionText(andesButton: AndesButton): String {
        val labelText = andesButton.text.orEmpty()
        val loadingText = andesButton.context.getString(R.string.andes_button_loading)

        return when (andesButton.isLoading) {
            true -> {
                "$labelText, $loadingText."
            }
            false -> {
                "$labelText."
            }
        }
    }
}
