package com.mercadolibre.android.andesui.button.size

import com.mercadolibre.android.andesui.button.AndesButton
import java.util.Locale

/**
 * Utility class that does two things: Defines the possible sizes an [AndesButton] can take because it's an enum, as you can see.
 * But as a bonus it gives you the proper implementation so you don't have to make any mapping.
 *
 * You ask me with, let's say 'SMALL', and then I'll give you a proper implementation of that size.
 *
 * @property size Possible sizes that an [AndesButton] may take.
 */
enum class AndesButtonSize {
    SMALL,
    MEDIUM,
    LARGE;

    companion object {
        fun fromString(value: String): AndesButtonSize = valueOf(value.toUpperCase(Locale.ROOT))
    }

    internal val size get() = getAndesButtonSize()

    private fun getAndesButtonSize(): AndesButtonSizeInterface {
        return when (this) {
            SMALL -> AndesSmallButtonSize()
            MEDIUM -> AndesMediumButtonSize()
            LARGE -> AndesLargeButtonSize()
        }
    }
}
