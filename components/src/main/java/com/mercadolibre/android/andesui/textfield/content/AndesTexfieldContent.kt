package com.mercadolibre.android.andesui.textfield.content

import java.util.Locale

/**
 * Utility class that does two things: Defines the possible states an [AndesTextfield] can take because it's an enum, as you can see.
 * But as a bonus it gives you the proper implementation so you don't have to make any mapping.
 *
 * You ask me with, let's say 'IDLE', and then I'll give you a proper implementation of that content.
 *
 * @property content Possible contents that an [AndesTextfield] may take.
 */

enum class AndesTextfieldLeftContent {
    PREFIX,
    ICON;

    companion object {
        fun fromString(value: String): AndesTextfieldLeftContent = valueOf(value.toUpperCase(Locale.ROOT))
    }

    internal val leftContent get() = getAndesTextfieldLeftContent()

    private fun getAndesTextfieldLeftContent(): AndesTextfieldContentInterface {
        return when (this) {
            PREFIX -> AndesPrefixTextfieldContent
            ICON -> AndesIconTextfieldContent
        }
    }
}

enum class AndesTextfieldRightContent {
    SUFFIX,
    ICON,
    TOOLTIP,
    VALIDATED,
    CLEAR,
    ACTION,
    INDETERMINATE,
    CHECKBOX;

    companion object {
        fun fromString(value: String): AndesTextfieldRightContent = valueOf(value.toUpperCase(Locale.ROOT))
    }

    internal val rightContent get() = getAndesTextfieldRightContent()

    private fun getAndesTextfieldRightContent(): AndesTextfieldContentInterface {
        return when (this) {
            SUFFIX -> AndesSuffixTextfieldContent
            ICON -> AndesIconTextfieldContent
            TOOLTIP -> AndesTooltipTextfieldContent
            VALIDATED -> AndesValidatedTextfieldContent
            CLEAR -> AndesClearTextfieldContent
            ACTION -> AndesActionTextfieldContent
            INDETERMINATE -> AndesIndeterminateTextfieldContent
            CHECKBOX -> AndesCheckboxTextfieldContent
        }
    }
}
