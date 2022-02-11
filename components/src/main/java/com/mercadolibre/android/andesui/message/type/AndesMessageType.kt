package com.mercadolibre.android.andesui.message.type

import java.util.Locale

/**
 * Utility class that does two things: Defines the possible styles an [AndesMessage] can take because it's an enum, as you can see.
 * But as a bonus it gives you the proper implementation so you don't have to make any mapping.
 *
 * You ask me with, let's say 'QUIET', and then I'll give you a proper implementation of that style.
 *
 * @property type Possible styles that an [AndesMessage] may take.
 */
enum class AndesMessageType {
    NEUTRAL,
    SUCCESS,
    WARNING,
    ERROR;

    companion object {
        fun fromString(value: String): AndesMessageType = valueOf(value.toUpperCase(Locale.ROOT))
    }

    internal val type get() = getAndesMessageHierarchy()

    private fun getAndesMessageHierarchy(): AndesMessageTypeInterface {
        return when (this) {
            NEUTRAL -> AndesNeutralMessageType
            SUCCESS -> AndesSuccessMessageType
            WARNING -> AndesWarningMessageType
            ERROR -> AndesErrorMessageType
        }
    }
}
