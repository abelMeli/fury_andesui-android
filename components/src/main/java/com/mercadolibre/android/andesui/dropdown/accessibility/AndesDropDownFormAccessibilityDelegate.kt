package com.mercadolibre.android.andesui.dropdown.accessibility

import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.dropdown.AndesDropDownForm
import com.mercadolibre.android.andesui.dropdown.state.AndesDropdownState

class AndesDropDownFormAccessibilityDelegate(private val andesDropDownForm: AndesDropDownForm) : View.AccessibilityDelegate() {

    override fun onInitializeAccessibilityNodeInfo(host: View?, info: AccessibilityNodeInfo?) {
        super.onInitializeAccessibilityNodeInfo(host, info)
        info?.contentDescription = generateContentDescriptionText(andesDropDownForm)
    }

    private fun generateContentDescriptionText(andesDropDownForm: AndesDropDownForm): String {
        val labelText = andesDropDownForm.label.orEmpty()
        val helperText = andesDropDownForm.helper.orEmpty()
        val innerText = getInnerText(andesDropDownForm)
        val errorText = getErrorText(andesDropDownForm)

        return "$labelText. $errorText, $helperText. $innerText"
    }

    private fun getInnerText(andesDropDownForm: AndesDropDownForm): String {
        andesDropDownForm.getSelectedItemTitle()?.let {
            if (it.isNotEmpty()) {
                return it
            }
        }
        return andesDropDownForm.placeholder.orEmpty()
    }

    private fun getErrorText(andesDropDownForm: AndesDropDownForm): String {
        return when (andesDropDownForm.state) {
            AndesDropdownState.ERROR -> andesDropDownForm
                .context
                .getString(R.string.andes_dropdown_error_text)
            else -> ""
        }
    }
}
