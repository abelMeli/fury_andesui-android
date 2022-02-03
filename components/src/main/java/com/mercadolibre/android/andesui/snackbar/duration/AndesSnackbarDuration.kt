package com.mercadolibre.android.andesui.snackbar.duration

/**
 * Utility class that does two things: Defines the possible styles an [AndesTag] can take because it's an enum, as you can see.
 * But as a bonus it gives you the proper implementation so you don't have to make any mapping.
 *
 * You ask me with, let's say 'QUIET', and then I'll give you a proper implementation of that style.
 *
 * @property type Possible styles that an [AndesTag] may take.
 */
enum class AndesSnackbarDuration {
    SHORT,
    NORMAL,
    LONG,
    INFINITE;

    companion object {
        fun fromString(value: String): AndesSnackbarDuration = valueOf(value.toUpperCase())
    }

    internal val duration get() = getAndesSnackbarDuration()

    private fun getAndesSnackbarDuration(): AndesSnackbarDurationInterface {
        return when (this) {
            SHORT -> AndesSnackbarShortDuration()
            NORMAL -> AndesSnackbarNormalDuration()
            LONG -> AndesSnackbarLongDuration()
            INFINITE -> AndesSnackbarInfiniteDuration()
        }
    }
}
