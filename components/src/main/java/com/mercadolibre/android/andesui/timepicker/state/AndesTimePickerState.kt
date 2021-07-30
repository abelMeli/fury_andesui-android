package com.mercadolibre.android.andesui.timepicker.state

/**
 * Utility class that does two things: Defines the possible states an [AndesTimePicker] can take because it's an enum,
 * as you can see.
 * But as a bonus it gives you the proper implementation so you don't have to make any mapping.
 *
 * You ask me with, let's say 'IDLE', and then I'll give you a proper implementation of that state.
 *
 * @property state Possible states that an [AndesTimePicker] may take.
 */
enum class AndesTimePickerState {
    IDLE,
    ERROR,
    DISABLED,
    READONLY;

    companion object {
        fun fromString(value: String): AndesTimePickerState = valueOf(value.toUpperCase())
    }

    internal val state get() = getAndesTimePickerState()

        private fun getAndesTimePickerState(): AndesTimePickerStateInterface {
            return when (this) {
                IDLE -> AndesIdleTimePickerState
                ERROR -> AndesErrorTimePickerState
                DISABLED -> AndesDisabledTimePickerState
                READONLY -> AndesReadOnlyTimePickerState
            }
        }
    }
