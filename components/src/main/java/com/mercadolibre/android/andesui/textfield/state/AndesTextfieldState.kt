package com.mercadolibre.android.andesui.textfield.state

import java.util.Locale

/**
 * Utility class that does two things: Defines the possible states an [AndesTextfield] can take because it's an enum, as you can see.
 * But as a bonus it gives you the proper implementation so you don't have to make any mapping.
 *
 * You ask me with, let's say 'IDLE', and then I'll give you a proper implementation of that state.
 *
 * @property state Possible states that an [AndesTextfield] may take.
 */
enum class AndesTextfieldState {
    IDLE,
    ERROR,
    DISABLED,
    READONLY;

    companion object {
        fun fromString(value: String): AndesTextfieldState = valueOf(value.toUpperCase(Locale.ROOT))
    }

    internal val state get() = getAndesTextfieldState()

    private fun getAndesTextfieldState(): AndesTextfieldStateInterface {
        return when (this) {
            IDLE -> AndesIdleTextfieldState
            ERROR -> AndesErrorTextfieldState
            DISABLED -> AndesDisabledTextfieldState
            READONLY -> AndesReadonlyTextfieldState
        }
    }
}
