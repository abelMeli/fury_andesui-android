package com.mercadolibre.android.andesui.thumbnail.hierarchy

import java.util.Locale

/**
 * Utility class that does two things: Defines the possible hierarchies an [AndesThumbnail] can take because it's an
 * enum, as you can see.
 * But as a bonus it gives you the proper implementation so you don't have to make any mapping.
 *
 * You ask me with, let's say 'QUIET', and then I'll give you a proper implementation of that hierarchy.
 *
 * @property hierarchy Possible hierarchies that an [AndesThumbnail] may take.
 */
enum class AndesThumbnailHierarchy {
    DEFAULT,
    QUIET,
    LOUD;

    companion object {
        fun fromString(value: String): AndesThumbnailHierarchy = valueOf(value.toUpperCase(Locale.ROOT))
    }

    internal val hierarchy get() = getAndesThumbnailHierarchy()

    private fun getAndesThumbnailHierarchy(): AndesThumbnailHierarchyInterface {
        return when (this) {
            DEFAULT -> AndesDefaultThumbnailHierarchy()
            QUIET -> AndesQuietThumbnailHierarchy()
            LOUD -> AndesLoudThumbnailHierarchy()
        }
    }
}
