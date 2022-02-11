package com.mercadolibre.android.andesui.tag.type

import java.util.Locale

/**
 * Utility class that does two things: Defines the possible styles an [AndesTag] can take because it's an enum, as you can see.
 * But as a bonus it gives you the proper implementation so you don't have to make any mapping.
 *
 * You ask me with, let's say 'QUIET', and then I'll give you a proper implementation of that style.
 *
 * @property type Possible styles that an [AndesTag] may take.
 */
enum class AndesTagType {
    NEUTRAL,
    HIGHLIGHT,
    SUCCESS,
    WARNING,
    ERROR;

    companion object {
        fun fromString(value: String): AndesTagType = valueOf(value.toUpperCase(Locale.ROOT))
    }

    internal val type get() = getAndesTagHierarchy()

    private fun getAndesTagHierarchy(): AndesSimpleTagTypeInterface {
        return when (this) {
            NEUTRAL -> AndesNeutralTagType()
            HIGHLIGHT -> AndesHighlightTagType()
            SUCCESS -> AndesSuccessTagType()
            WARNING -> AndesWarningTagType()
            ERROR -> AndesErrorTagType()
        }
    }
}
