package com.mercadolibre.android.andesui.utils

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import com.mercadolibre.android.andesui.button.AndesButton

internal fun AndesButton.getBackgroundDrawable(): Drawable {
    return hierarchy.hierarchy.background(context, size.size.cornerRadius(context))
}

internal fun AndesButton.getTextColor(): ColorStateList {
    return hierarchy.hierarchy.textColor(context)
}
