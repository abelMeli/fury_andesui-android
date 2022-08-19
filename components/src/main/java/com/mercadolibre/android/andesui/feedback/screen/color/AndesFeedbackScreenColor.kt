package com.mercadolibre.android.andesui.feedback.screen.color

import com.mercadolibre.android.andesui.feedback.screen.type.AndesFeedbackBadgeIconType
import java.util.Locale

enum class AndesFeedbackScreenColor(internal val color: AndesFeedbackBadgeIconType) {
    GREEN(AndesFeedbackBadgeIconType.SUCCESS),
    ORANGE(AndesFeedbackBadgeIconType.WARNING),
    RED(AndesFeedbackBadgeIconType.ERROR);

    companion object {
        fun fromString(value: String): AndesFeedbackScreenColor = valueOf(value.toUpperCase(Locale.ROOT))
    }
}
