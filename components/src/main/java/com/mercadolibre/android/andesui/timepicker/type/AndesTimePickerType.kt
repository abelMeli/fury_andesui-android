package com.mercadolibre.android.andesui.timepicker.type

/**
 * Utility class that does two things: Defines the possible styles an [AndesTimePicker] can take because it's an enum,
 * as you can see.
 * But as a bonus it gives you the proper implementation so you don't have to make any mapping.
 *
 * You ask me with, let's say 'TIME_INTERVAL', and then I'll give you a proper implementation of that type.
 *
 * @property type Possible styles that an [AndesTimePicker] may take.
 */
enum class AndesTimePickerType {
    TIME_INTERVAL;

    companion object {
        fun fromString(value: String): AndesTimePickerType = valueOf(value.toUpperCase())
    }

    internal val type get() = getAndesTimePickerType()

    private fun getAndesTimePickerType(): AndesTimePickerTypeInterface {
        return when (this) {
            TIME_INTERVAL -> AndesTimeIntervalTimePickerType
        }
    }
}
