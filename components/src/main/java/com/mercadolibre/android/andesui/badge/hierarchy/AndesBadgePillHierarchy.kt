package com.mercadolibre.android.andesui.badge.hierarchy

import java.util.Locale

/**
 * Utility class that does two things: Defines the possible hierarchies an [AndesBadge] can take because it's an enum, as you can see.
 * But as a bonus it gives you the proper implementation so you don't have to make any mapping.
 *
 * You ask me with, let's say 'QUIET', and then I'll give you a proper implementation of that hierarchy.
 *
 * @property hierarchy Possible hierarchies that an [AndesBadge] may take.
 */
enum class AndesBadgePillHierarchy {
    QUIET,
    LOUD;

    companion object {
        fun fromString(value: String): AndesBadgePillHierarchy = valueOf(value.toUpperCase(Locale.ROOT))
    }

    internal val hierarchy get() = getAndesBadgePillHierarchy()

    private fun getAndesBadgePillHierarchy(): AndesBadgePillHierarchyInterface {
        return when (this) {
            QUIET -> AndesQuietBadgeHierarchy()
            LOUD -> AndesLoudBadgeHierarchy()
        }
    }
}
