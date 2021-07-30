package com.mercadolibre.android.andesui.timepicker.minutesInterval

import com.mercadolibre.android.andesui.timepicker.utils.TimeUtils.HOURS_LONG
import com.mercadolibre.android.andesui.timepicker.utils.TimeUtils.MINUTES_10
import com.mercadolibre.android.andesui.timepicker.utils.TimeUtils.MINUTES_15
import com.mercadolibre.android.andesui.timepicker.utils.TimeUtils.MINUTES_30
import com.mercadolibre.android.andesui.timepicker.utils.TimeUtils.MINUTES_5
import com.mercadolibre.android.andesui.timepicker.utils.TimeUtils.MINUTES_60
import com.mercadolibre.android.andesui.timepicker.utils.TimeUtils.createFullList

/**
 * Defines all style related properties that an [AndesTimePicker] needs to be drawn properly.
 * Those properties change depending on the style of the message.
 */
internal interface AndesTimePickerIntervalInterface {
    fun hours(): ArrayList<String>
    fun minutes(): ArrayList<String>
    fun full(): ArrayList<String>
}

internal object AndesTimePicker5MinutesInterval : AndesTimePickerIntervalInterface {
    override fun hours() = HOURS_LONG
    override fun minutes() = MINUTES_5
    override fun full() = createFullList(HOURS_LONG, MINUTES_5)
}

internal object AndesTimePicker10MinutesInterval : AndesTimePickerIntervalInterface {
    override fun hours() = HOURS_LONG
    override fun minutes() = MINUTES_10
    override fun full() = createFullList(HOURS_LONG, MINUTES_10)
}

internal object AndesTimePicker15MinutesInterval : AndesTimePickerIntervalInterface {
    override fun hours() = HOURS_LONG
    override fun minutes() = MINUTES_15
    override fun full() = createFullList(HOURS_LONG, MINUTES_15)
}

internal object AndesTimePicker30MinutesInterval : AndesTimePickerIntervalInterface {
    override fun hours() = HOURS_LONG
    override fun minutes() = MINUTES_30
    override fun full() = createFullList(HOURS_LONG, MINUTES_30)
}

internal object AndesTimePicker60MinutesInterval : AndesTimePickerIntervalInterface {
    override fun hours() = HOURS_LONG
    override fun minutes() = MINUTES_60
    override fun full() = createFullList(HOURS_LONG, MINUTES_60)
}
