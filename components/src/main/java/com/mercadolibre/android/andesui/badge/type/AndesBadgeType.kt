package com.mercadolibre.android.andesui.badge.type

import java.util.Locale

/**
 * Utility class that does two things: Defines the possible styles an [AndesBadge] can take because it's an enum, as you can see.
 * But as a bonus it gives you the proper implementation so you don't have to make any mapping.
 *
 * You ask me with, let's say 'QUIET', and then I'll give you a proper implementation of that style.
 *
 * @property type Possible styles that an [AndesBadge] may take.
 */
enum class AndesBadgeType {
    NEUTRAL,
    HIGHLIGHT,
    SUCCESS,
    WARNING,
    ERROR;

    companion object {
        fun fromString(value: String): AndesBadgeType = valueOf(value.toUpperCase(Locale.ROOT))
    }

    internal val type get() = getAndesBadgeHierarchy()

    private fun getAndesBadgeHierarchy(): AndesBadgeTypeInterface {
        return when (this) {
            NEUTRAL -> AndesNeutralBadgeType()
            HIGHLIGHT -> AndesHighlightBadgeType()
            SUCCESS -> AndesSuccessBadgeType()
            WARNING -> AndesWarningBadgeType()
            ERROR -> AndesErrorBadgeType()
        }
    }
}
