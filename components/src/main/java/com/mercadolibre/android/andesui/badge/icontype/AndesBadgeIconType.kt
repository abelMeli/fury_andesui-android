package com.mercadolibre.android.andesui.badge.icontype

import com.mercadolibre.android.andesui.badge.type.AndesBadgeType

/**
 * Utility class that does two things: Defines the possible styles an [AndesBadgeIconPill] can take because it's an enum, as you can see.
 * But as a bonus it gives you the proper implementation so you don't have to make any mapping.
 *
 * You ask me with, let's say 'HIGHLIGHT', and then I'll give you a proper implementation of that style.
 *
 * @property type Possible styles that an [AndesBadge] may take.
 */
enum class AndesBadgeIconType {
    HIGHLIGHT,
    SUCCESS,
    WARNING,
    ERROR;

    companion object {
        fun fromString(value: String): AndesBadgeIconType = valueOf(value.toUpperCase())
    }

    internal val iconType get() = getAndesBadgeType()

    private fun getAndesBadgeType(): AndesBadgeType {
        return when (this) {
            HIGHLIGHT -> AndesBadgeType.HIGHLIGHT
            SUCCESS -> AndesBadgeType.SUCCESS
            WARNING -> AndesBadgeType.WARNING
            ERROR -> AndesBadgeType.ERROR
        }
    }
}
