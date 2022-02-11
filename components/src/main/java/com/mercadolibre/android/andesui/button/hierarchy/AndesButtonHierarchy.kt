package com.mercadolibre.android.andesui.button.hierarchy

import com.mercadolibre.android.andesui.button.AndesButton
import java.util.Locale

/**
 * Utility class that does two things: Defines the possible hierarchies an [AndesButton] can take because it's an enum, as you can see.
 * But as a bonus it gives you the proper implementation so you don't have to make any mapping.
 *
 * You ask me with, let's say 'QUIET', and then I'll give you a proper implementation of that hierarchy.
 *
 * @property hierarchy Possible hierarchies that an [AndesButton] may take.
 */
enum class AndesButtonHierarchy {
    TRANSPARENT,
    QUIET,
    LOUD;

    companion object {
        fun fromString(value: String): AndesButtonHierarchy = valueOf(value.toUpperCase(Locale.ROOT))
    }

    internal val hierarchy get() = getAndesButtonHierarchy()

    private fun getAndesButtonHierarchy(): AndesButtonHierarchyInterface {
        return when (this) {
            TRANSPARENT -> AndesTransparentButtonHierarchy
            QUIET -> AndesQuietButtonHierarchy
            LOUD -> AndesLoudButtonHierarchy
        }
    }
}
