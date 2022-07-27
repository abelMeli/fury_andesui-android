package com.mercadolibre.android.andesui.textfield.accessibility

import android.os.Build
import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.TextView
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.textfield.AndesTextfield
import com.mercadolibre.android.andesui.textfield.content.AndesTextfieldLeftContent
import com.mercadolibre.android.andesui.textfield.content.AndesTextfieldRightContent
import com.mercadolibre.android.andesui.textfield.state.AndesTextfieldState
import com.mercadolibre.android.andesui.utils.append

/**
 * class responsible for generating the content description for the andesTextfield, according
 * to the available parameters (label, helper, counter, state, among others)
 */
internal class AndesTextfieldAccessibilityDelegate(private val andesTextfield: AndesTextfield) : View.AccessibilityDelegate() {

    /**
     * method responsible for sending the component description to the Talkback service.
     */
    override fun onInitializeAccessibilityNodeInfo(host: View?, info: AccessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(host, info)
        info.contentDescription = generateContentDescriptionText(andesTextfield)

        val wrappedInfo = AccessibilityNodeInfoCompat.wrap(info)
        wrappedInfo.isTextEntryKey = false
    }

    /**
     * method responsible for generating the string needed as contentDescription.
     *
     * The returned string varies according to the state and the presence/absence of the
     * label, helper, placeholder, counter and entered text.
     */
    private fun generateContentDescriptionText(andesTextfield: AndesTextfield): String {
        val labelText = andesTextfield.label.orEmpty().let {
            if (it.isEmpty()) { it } else { it.append(',') }
        }

        val helperText = andesTextfield.helper.orEmpty().let {
            if (it.isEmpty()) { it } else { it.append(',') }
        }

        val placeholderText = andesTextfield.placeholder.orEmpty()
        val prefixText = getPrefixText(andesTextfield)
        val suffixText = getSuffixText(andesTextfield)
        val enteredText = getEnteredText(andesTextfield)
        val counterText = getCounterText(andesTextfield.counter, enteredText, andesTextfield.showCounter)
        val innerText = getInnerText(enteredText, placeholderText)
        val errorText = getErrorText()

        return when (andesTextfield.state) {
            AndesTextfieldState.IDLE, AndesTextfieldState.DISABLED -> {
                "$labelText $helperText $prefixText $innerText $suffixText $counterText."
            }
            AndesTextfieldState.READONLY -> {
                "$labelText $prefixText $innerText"
            }
            AndesTextfieldState.ERROR -> {
                "$labelText $errorText $helperText $prefixText $innerText $suffixText $counterText."
            }
        }
    }

    private fun getCounterText(counter: Int, enteredText: String, showCounter: Boolean): String {
        return if (counter != 0 && showCounter) {
            if (enteredText.isEmpty()) {
                ",${andesTextfield.context.getString(R.string.andes_textfield_char_number_text, andesTextfield.counter)}"
            } else {
                ",${
                    andesTextfield.context.getString(
                    R.string.andes_textfield_chars_entered,
                    enteredText.length,
                    andesTextfield.counter
                )}"
            }
        } else {
            EMPTY_STRING
        }
    }

    private fun getPrefixText(andesTextfield: AndesTextfield): String {
        return if (andesTextfield.leftContent == AndesTextfieldLeftContent.PREFIX) {
            (andesTextfield.leftComponent.getChildAt(0) as TextView).text.append(',').toString()
        } else {
            EMPTY_STRING
        }
    }

    private fun getSuffixText(andesTextfield: AndesTextfield): String {
        return if (andesTextfield.rightContent == AndesTextfieldRightContent.SUFFIX) {
            (andesTextfield.rightComponent.getChildAt(0) as TextView).text.append(',').toString()
        } else {
            EMPTY_STRING
        }
    }

    private fun getErrorText(): String {
        return "${andesTextfield.context.getString(R.string.andes_textfield_error_text)},"
    }

    private fun getEnteredText(andesTextfield: AndesTextfield): String {
        return andesTextfield.textComponent.text.toString()
    }

    private fun getInnerText(enteredText: String, placeholderText: String): String {
        return enteredText.ifEmpty {
            placeholderText
        }
    }

    companion object {
        private const val EMPTY_STRING = ""
    }
}
