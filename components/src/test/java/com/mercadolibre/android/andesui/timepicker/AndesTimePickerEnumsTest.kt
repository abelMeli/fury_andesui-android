package com.mercadolibre.android.andesui.timepicker

import com.mercadolibre.android.andesui.utils.assertEquals
import com.mercadolibre.android.andesui.timepicker.minutesInterval.AndesTimePickerInterval
import com.mercadolibre.android.andesui.timepicker.state.AndesTimePickerState
import com.mercadolibre.android.andesui.timepicker.type.AndesTimePickerType
import java.lang.IllegalArgumentException
import org.junit.Assert
import org.junit.Test

class AndesTimePickerEnumsTest {

    @Test
    fun `AndesTimePickerState fromString parse valid`() {
        // WHEN
        val idle = AndesTimePickerState.fromString("idle")
        val error = AndesTimePickerState.fromString("error")
        val disabled = AndesTimePickerState.fromString("disabled")
        val readonly = AndesTimePickerState.fromString("readonly")

        // THEN
        idle assertEquals AndesTimePickerState.IDLE
        error assertEquals AndesTimePickerState.ERROR
        disabled assertEquals AndesTimePickerState.DISABLED
        readonly assertEquals AndesTimePickerState.READONLY
    }

    @Test
    fun `AndesTimePickerState fromString parse invalid`() {
        // WHEN - THEN
        Assert.assertThrows(IllegalArgumentException::class.java) {
            AndesTimePickerState.fromString("read_only")
        }
    }

    @Test
    fun `AndesTimePickerInterval fromString parse valid`() {
        // WHEN
        val minutes5 = AndesTimePickerInterval.fromString("minutes_5")
        val minutes10 = AndesTimePickerInterval.fromString("minutes_10")
        val minutes15 = AndesTimePickerInterval.fromString("minutes_15")
        val minutes30 = AndesTimePickerInterval.fromString("minutes_30")
        val minutes60 = AndesTimePickerInterval.fromString("minutes_60")

        // THEN
        minutes5 assertEquals AndesTimePickerInterval.MINUTES_5
        minutes10 assertEquals AndesTimePickerInterval.MINUTES_10
        minutes15 assertEquals AndesTimePickerInterval.MINUTES_15
        minutes30 assertEquals AndesTimePickerInterval.MINUTES_30
        minutes60 assertEquals AndesTimePickerInterval.MINUTES_60
    }

    @Test
    fun `AndesTimePickerInterval fromString parse invalid`() {
        // WHEN - THEN
        Assert.assertThrows(IllegalArgumentException::class.java) {
            AndesTimePickerInterval.fromString("enable")
        }
    }

    @Test
    fun `AndesTimePickerType fromString parse valid`() {
        // WHEN
        val timeInterval = AndesTimePickerType.fromString("time_interval")

        // THEN
        timeInterval assertEquals AndesTimePickerType.TIME_INTERVAL
    }

    @Test
    fun `AndesTimePickerType fromString parse invalid`() {
        // WHEN - THEN
        Assert.assertThrows(IllegalArgumentException::class.java) {
            AndesTimePickerType.fromString("timeintervaal")
        }
    }
}
