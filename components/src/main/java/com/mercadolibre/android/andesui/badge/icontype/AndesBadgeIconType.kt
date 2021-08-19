package com.mercadolibre.android.andesui.badge.icontype

import com.mercadolibre.android.andesui.badge.type.AndesBadgeTypeInterface
import com.mercadolibre.android.andesui.badge.type.AndesErrorBadgeType
import com.mercadolibre.android.andesui.badge.type.AndesHighlightBadgeType
import com.mercadolibre.android.andesui.badge.type.AndesSuccessBadgeType
import com.mercadolibre.android.andesui.badge.type.AndesWarningBadgeType

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

    internal val type get() = getAndesBadgeType()

    private fun getAndesBadgeType(): AndesBadgeTypeInterface {
        return when (this) {
            HIGHLIGHT -> AndesHighlightBadgeType()
            SUCCESS -> AndesSuccessBadgeType()
            WARNING -> AndesWarningBadgeType()
            ERROR -> AndesErrorBadgeType()
        }
    }
}
