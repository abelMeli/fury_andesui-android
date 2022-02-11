package com.mercadolibre.android.andesui.badge.hierarchy

import com.mercadolibre.android.andesui.badge.AndesBadgeIconPill
import java.util.Locale

/**
 * Utility class that does two things: Defines the possible hierarchies an [AndesBadgeIconPill] can take because it's an enum, as you can see.
 * But as a bonus it gives you the proper implementation so you don't have to make any mapping.
 *
 * You ask me with, let's say 'LOUD', and then I'll give you a proper implementation of that hierarchy.
 *
 * @property hierarchy Possible hierarchies that an [AndesBadgeIconPill] may take.
 */
enum class AndesBadgeIconHierarchy {
    LOUD,
    SECONDARY;

    companion object {
        fun fromString(value: String): AndesBadgeIconHierarchy = valueOf(value.toUpperCase(Locale.ROOT))
    }

    internal val hierarchy get() = getAndesBadgeIconHierarchy()

    private fun getAndesBadgeIconHierarchy(): AndesBadgeIconHierarchyInterface {
        return when (this) {
            LOUD -> AndesLoudBadgeIconHierarchy()
            SECONDARY -> AndesSecondaryBadgeIconHierarchy()
        }
    }
}
