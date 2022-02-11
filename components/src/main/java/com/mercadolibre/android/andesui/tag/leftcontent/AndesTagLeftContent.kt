package com.mercadolibre.android.andesui.tag.leftcontent

import java.util.Locale

/**
 * Utility class that does two things: Defines the possible states an [AndesTag] can take because it's an enum, as you can see.
 * But as a bonus it gives you the proper implementation so you don't have to make any mapping.
 *
 * You ask me with, let's say 'IDLE', and then I'll give you a proper implementation of that content.
 */

enum class AndesTagLeftContent {
    DOT,
    ICON,
    IMAGE,
    NONE;

    companion object {
        fun fromString(value: String): AndesTagLeftContent = valueOf(value.toUpperCase(Locale.ROOT))
    }

    internal val content get() = getAndesTagLeftContent()

    private fun getAndesTagLeftContent(): AndesTagLeftContentInterface {
        return when (this) {
            DOT -> AndesTagLeftContentDot
            ICON -> AndesTagLeftContentIcon
            IMAGE -> AndesTagLeftContentImage
            NONE -> AndesTagLeftContentNone
        }
    }
}
