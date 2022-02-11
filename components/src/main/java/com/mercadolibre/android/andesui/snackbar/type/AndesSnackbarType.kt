package com.mercadolibre.android.andesui.snackbar.type

import java.util.Locale

/**
 * Utility class that does two things: Defines the possible styles an [AndesTag] can take because it's an enum, as you can see.
 * But as a bonus it gives you the proper implementation so you don't have to make any mapping.
 *
 * You ask me with, let's say 'QUIET', and then I'll give you a proper implementation of that style.
 *
 * @property type Possible styles that an [AndesTag] may take.
 */
enum class AndesSnackbarType {
    NEUTRAL,
    ERROR,
    SUCCESS;

    companion object {
        fun fromString(value: String): AndesSnackbarType = valueOf(value.toUpperCase(Locale.ROOT))
    }

    internal val type get() = getAndesTagHierarchy()

    private fun getAndesTagHierarchy(): AndesSnackbarTypeInterface {
        return when (this) {
            NEUTRAL -> AndesSnackbarNeutralType()
            SUCCESS -> AndesSnackbarSuccessType()
            ERROR -> AndesSnackbarErrorType()
        }
    }
}
