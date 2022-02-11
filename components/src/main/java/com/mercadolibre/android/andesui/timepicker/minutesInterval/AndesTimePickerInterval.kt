package com.mercadolibre.android.andesui.timepicker.minutesInterval

import java.util.Locale

/**
 * Utility class that does two things: Defines the possible states an [AndesTimePicker] can take because it's an enum,
 * as you can see.
 * But as a bonus it gives you the proper implementation so you don't have to make any mapping.
 *
 * You ask me with, let's say 'MINUTES_5', and then I'll give you a proper implementation of that state.
 *
 * @property interval Possible states that an [AndesTimePicker] may take.
 */
enum class AndesTimePickerInterval {
    MINUTES_5,
    MINUTES_10,
    MINUTES_15,
    MINUTES_30,
    MINUTES_60;

    companion object {
        fun fromString(value: String): AndesTimePickerInterval = valueOf(value.toUpperCase(Locale.ROOT))
    }

    internal val interval get() = getAndesTimePickerFormat()

    private fun getAndesTimePickerFormat(): AndesTimePickerIntervalInterface {
        return when (this) {
            MINUTES_5 -> AndesTimePicker5MinutesInterval
            MINUTES_10 -> AndesTimePicker10MinutesInterval
            MINUTES_15 -> AndesTimePicker15MinutesInterval
            MINUTES_30 -> AndesTimePicker30MinutesInterval
            MINUTES_60 -> AndesTimePicker60MinutesInterval
        }
    }
}
