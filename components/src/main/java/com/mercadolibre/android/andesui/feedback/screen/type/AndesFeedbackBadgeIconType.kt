package com.mercadolibre.android.andesui.feedback.screen.type

import com.mercadolibre.android.andesui.badge.type.AndesBadgeType

internal enum class AndesFeedbackBadgeIconType(internal val type: AndesBadgeType) {
    HIGHLIGHT(AndesBadgeType.HIGHLIGHT),
    SUCCESS(AndesBadgeType.SUCCESS),
    WARNING(AndesBadgeType.WARNING),
    NEUTRAL(AndesBadgeType.NEUTRAL),
    ERROR(AndesBadgeType.ERROR);
}