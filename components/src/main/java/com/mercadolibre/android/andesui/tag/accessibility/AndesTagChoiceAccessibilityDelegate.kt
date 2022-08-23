package com.mercadolibre.android.andesui.tag.accessibility

import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import com.mercadolibre.android.andesui.tag.AndesTagChoice
import com.mercadolibre.android.andesui.tag.choice.state.AndesTagChoiceState

internal class AndesTagChoiceAccessibilityDelegate(private val tagChoice: AndesTagChoice) : View.AccessibilityDelegate() {

    override fun onInitializeAccessibilityNodeInfo(host: View?, info: AccessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(host, info)
        info.contentDescription = "${tagChoice.text}${resolveAssetContentDescription()}"
        info.isCheckable = true
        info.isChecked = resolveIsChecked()
    }

    private fun resolveAssetContentDescription(): String {
        val contentString = tagChoice.leftContent?.assetContentDescription ?: return EMPTY_STRING
        return if(contentString.isBlank()) EMPTY_STRING else ", $contentString"
    }

    private fun resolveIsChecked() = tagChoice.state == AndesTagChoiceState.SELECTED

    companion object {
        private const val EMPTY_STRING = ""
    }

}