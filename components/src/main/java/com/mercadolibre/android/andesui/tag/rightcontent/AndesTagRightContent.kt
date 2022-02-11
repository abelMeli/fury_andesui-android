package com.mercadolibre.android.andesui.tag.rightcontent

import java.util.Locale

/**
 * Utility class that does two things: Defines the possible states an [AndesTag] can take because it's an enum, as you can see.
 * But as a bonus it gives you the proper implementation so you don't have to make any mapping.
 *
 * You ask me with, let's say 'IDLE', and then I'll give you a proper implementation of that content.
 *
 * @property content Possible contents that an [AndesTag] may take.
 */
enum class AndesTagRightContent {
    DISMISS,
    DROPDOWN,
    CHECK,
    NONE;

    companion object {
        fun fromString(value: String): AndesTagRightContent = valueOf(value.toUpperCase(Locale.ROOT))
    }

    internal val content get() = getAndesTagRightContent()

    private fun getAndesTagRightContent(): AndesTagRightContentInterface {
        return when (this) {
            DISMISS -> AndesTagRightContentDismiss
            DROPDOWN -> AndesTagRightContentDropDown
            CHECK -> AndesTagRightContentCheck
            NONE -> AndesTagRightContentNone
        }
    }
}
