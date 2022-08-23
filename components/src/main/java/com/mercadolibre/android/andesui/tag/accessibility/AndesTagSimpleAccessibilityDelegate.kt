package com.mercadolibre.android.andesui.tag.accessibility

import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import com.mercadolibre.android.andesui.tag.AndesTagSimple

internal class AndesTagSimpleAccessibilityDelegate(private val tagSimple: AndesTagSimple) : View.AccessibilityDelegate() {

    override fun onInitializeAccessibilityNodeInfo(host: View?, info: AccessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(host, info)

        info.contentDescription = "${tagSimple.text}${resolveAssetContentDescription()}"
    }

    private fun resolveAssetContentDescription(): String {
        val contentString = tagSimple.leftContent?.assetContentDescription ?: return EMPTY_STRING
        return if(contentString.isBlank()) EMPTY_STRING else ", $contentString"
    }

    companion object {
        private const val EMPTY_STRING = ""
    }

}