package com.mercadolibre.android.andesui.stickyscrollview.utils

import android.view.View
import androidx.core.view.ViewCompat

internal object AndesStickyScrollViewUtils {
    fun setTranslationZ(view: View, translationZ: Float) {
        ViewCompat.setTranslationZ(view, translationZ)
    }
}
