package com.mercadolibre.android.andesui.radiobutton.accessibility

import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.radiobutton.AndesRadioButton
import com.mercadolibre.android.andesui.radiobutton.status.AndesRadioButtonStatus
import com.mercadolibre.android.andesui.radiobutton.type.AndesRadioButtonType

class AndesRadioButtonAccessibilityDelegate(private val andesRadioButton: AndesRadioButton) :
    View.AccessibilityDelegate() {

    override fun onInitializeAccessibilityNodeInfo(host: View?, info: AccessibilityNodeInfo?) {
        super.onInitializeAccessibilityNodeInfo(host, info)
        info?.contentDescription = generateContentDescriptionText(andesRadioButton)
        info?.isCheckable = true
        info?.isChecked = resolveIsChecked(andesRadioButton)
    }

    private fun generateContentDescriptionText(andesRadioButton: AndesRadioButton): String {
        val text = andesRadioButton.text.orEmpty()
        val errorText = getErrorText(andesRadioButton)
        return "$text. $errorText."
    }

    private fun resolveIsChecked(andesRadioButton: AndesRadioButton): Boolean {
        return andesRadioButton.status == AndesRadioButtonStatus.SELECTED
    }

    private fun getErrorText(andesRadioButton: AndesRadioButton): String {
        return when (andesRadioButton.type) {
            AndesRadioButtonType.ERROR -> {
                andesRadioButton.context.resources.getString(R.string.andes_radiobutton_type_error)
            }
            else -> EMPTY_STRING
        }
    }

    companion object {
        private const val EMPTY_STRING = ""
    }
}
