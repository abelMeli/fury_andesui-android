package com.mercadolibre.android.andesui.feedback.screen.color

import com.mercadolibre.android.andesui.badge.icontype.AndesBadgeIconType

enum class AndesFeedbackScreenColor(internal val color: AndesBadgeIconType) {
    GREEN(AndesBadgeIconType.SUCCESS),
    ORANGE(AndesBadgeIconType.WARNING),
    RED(AndesBadgeIconType.ERROR);

    companion object {
        fun fromString(value: String): AndesFeedbackScreenColor = valueOf(value.toUpperCase())
    }
}
