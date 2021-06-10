package com.mercadolibre.android.andesui.textfield.accessibility

import android.content.Context
import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.textfield.AndesTextfieldCode
import com.mercadolibre.android.andesui.textfield.state.AndesTextfieldCodeState
import com.mercadolibre.android.andesui.textfield.style.AndesTextfieldCodeStyle

/**
 * class responsible for generating the content description for the andesTextfieldCode, according
 * to the available parameters (label, helper, counter, state, among others)
 */
internal class AndesTextfieldCodeAccessibilityDelegate(private val andesTextfieldCode: AndesTextfieldCode) : View.AccessibilityDelegate() {

    /**
     * method responsible for sending the component description to the Talkback service.
     */
    override fun onInitializeAccessibilityNodeInfo(host: View?, info: AccessibilityNodeInfo?) {
        super.onInitializeAccessibilityNodeInfo(host, info)
        info?.contentDescription = generateContentDescriptionText(andesTextfieldCode)
    }

    /**
     * method responsible for generating the string needed as contentDescription.
     *
     * The returned string varies according to the state and the presence/absence of the
     * label, helper, style, state and entered text.
     */
    private fun generateContentDescriptionText(andesTextfieldCode: AndesTextfieldCode): String {
        val label = andesTextfieldCode.label.orEmpty()
        val helper = andesTextfieldCode.helper.orEmpty()
        val enteredText = andesTextfieldCode.text.orEmpty()
        val count = getCount(andesTextfieldCode)
        val counterText = getCounterText(andesTextfieldCode.context, count, enteredText)
        val errorText = getErrorText(andesTextfieldCode.context)

        return when (andesTextfieldCode.state) {
            AndesTextfieldCodeState.IDLE -> {
                "$label $helper. $enteredText, $counterText"
            }
            AndesTextfieldCodeState.ERROR -> {
                "$label. $errorText, $helper. $enteredText. $counterText"
            }
            AndesTextfieldCodeState.DISABLED -> {
                "$label, $helper."
            }
        }
    }

    private fun getCounterText(context: Context, counter: Int, enteredText: String): String {
        return context.getString(
                R.string.andes_textfield_chars_entered,
                enteredText.length,
                counter)
    }

    private fun getCount(andesTextfieldCode: AndesTextfieldCode): Int {
        return when (andesTextfieldCode.style) {
            AndesTextfieldCodeStyle.THREESOME -> {
                THREE_BOXES
            }
            AndesTextfieldCodeStyle.FOURSOME -> {
                FOUR_BOXES
            }
            AndesTextfieldCodeStyle.THREE_BY_THREE -> {
                SIX_BOXES
            }
        }
    }

    private fun getErrorText(context: Context): String {
        return context.getString(R.string.andes_textfield_error_text)
    }

    companion object {
        private const val THREE_BOXES = 3
        private const val FOUR_BOXES = 4
        private const val SIX_BOXES = 6
    }
}
