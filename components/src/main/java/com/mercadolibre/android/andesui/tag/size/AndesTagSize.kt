package com.mercadolibre.android.andesui.tag.size

import java.util.Locale

/**
 * Utility class that does two things: Defines the possible sizes an [AndesTag] can take because it's an enum, as you can see.
 * But as a bonus it gives you the proper implementation so you don't have to make any mapping.
 *
 * You ask me with, let's say 'LARGE', and then I'll give you a proper implementation of that size.
 *
 * @property size Possible sizes that an [AndesTag] may take.
 */
enum class AndesTagSize {
    SMALL,
    LARGE;

    companion object {
        fun fromString(value: String): AndesTagSize = valueOf(value.toUpperCase(Locale.ROOT))
    }

    internal val size get() = getAndesTagSize()

    private fun getAndesTagSize(): AndesTagSizeInterface {
        return when (this) {
            SMALL -> AndesSmallTagSize()
            LARGE -> AndesLargeTagSize()
        }
    }
}
