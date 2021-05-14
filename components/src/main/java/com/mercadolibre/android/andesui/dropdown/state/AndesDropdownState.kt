package com.mercadolibre.android.andesui.dropdown.state

/**
 * Utility class that does two things: Defines the possible states an [AndesDropdown] can take because it's an enum, as you can see.
 * But as a bonus it gives you the proper implementation so you don't have to make any mapping.
 *
 * You ask me with, let's say 'ENABLED', and then I'll give you a proper implementation of that state.
 *
 * @property state Possible states that an [AndesDropdown] may take.
 */
enum class AndesDropdownState {
    ENABLED,
    ERROR,
    DISABLED;

    companion object {
        fun fromString(value: String): AndesDropdownState = valueOf(value.toUpperCase())
    }

    internal val state get() = getAndesDropdowmState()

    private fun getAndesDropdowmState(): AndesDropdownStateInterface {
        return when (this) {
            ENABLED -> AndesEnabledDropdownState
            ERROR -> AndesErrorDropdownState
            DISABLED -> AndesDisabledDropdownState
        }
    }
}
