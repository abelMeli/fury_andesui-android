package com.mercadolibre.android.andesui.datepicker.startofweek

enum class AndesDatePickerStartOfWeek(val value: Int) {
    SUNDAY(1),
    MONDAY(2);

    companion object {
        fun fromInt(value: Int) = values().first { it.value == value }
    }
}
