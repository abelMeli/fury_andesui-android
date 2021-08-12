package com.mercadolibre.android.andesui.utils

import android.content.Context
import android.view.accessibility.AccessibilityManager

internal fun Context.getAccessibilityManager(): AccessibilityManager {
    return (getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager)
}
