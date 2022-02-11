package com.mercadolibre.android.andesui.radiobutton

import android.os.Build
import com.mercadolibre.android.andesui.assertEquals
import com.mercadolibre.android.andesui.radiobutton.align.AndesRadioButtonAlign
import com.mercadolibre.android.andesui.radiobutton.status.AndesRadioButtonStatus
import com.mercadolibre.android.andesui.radiobutton.type.AndesRadioButtonType
import java.lang.IllegalArgumentException
import org.junit.Assert
import org.junit.Test

class AndesRadioButtonEnumsTest {

    @Test
    fun `AndesRadioButtonAlign fromString parse valid`() {
        // WHEN
        val left = AndesRadioButtonAlign.fromString("left")
        val right = AndesRadioButtonAlign.fromString("right")

        // THEN
        left assertEquals AndesRadioButtonAlign.LEFT
        right assertEquals AndesRadioButtonAlign.RIGHT
    }

    @Test
    fun `AndesRadioButtonAlign fromString parse invalid`() {
        // WHEN - THEN
        Assert.assertThrows(IllegalArgumentException::class.java) {
            AndesRadioButtonAlign.fromString("center")
        }
    }

    @Test
    fun `AndesRadioButtonStatus fromString parse valid`() {
        // WHEN
        val selected = AndesRadioButtonStatus.fromString("selected")
        val unselected = AndesRadioButtonStatus.fromString("unselected")

        // THEN
        selected assertEquals AndesRadioButtonStatus.SELECTED
        unselected assertEquals AndesRadioButtonStatus.UNSELECTED
    }

    @Test
    fun `AndesRadioButtonStatus fromString parse invalid`() {
        // WHEN - THEN
        Assert.assertThrows(IllegalArgumentException::class.java) {
            AndesRadioButtonStatus.fromString("un_selected")
        }
    }

    @Test
    fun `AndesRadioButtonType fromString parse valid`() {
        // WHEN
        val idle = AndesRadioButtonType.fromString("idle")
        val disabled = AndesRadioButtonType.fromString("disabled")
        val error = AndesRadioButtonType.fromString("error")

        // THEN
        idle assertEquals AndesRadioButtonType.IDLE
        disabled assertEquals AndesRadioButtonType.DISABLED
        error assertEquals AndesRadioButtonType.ERROR
    }

    @Test
    fun `AndesRadioButtonType fromString parse invalid`() {
        // WHEN - THEN
        Assert.assertThrows(IllegalArgumentException::class.java) {
            AndesRadioButtonType.fromString("disable")
        }
    }
}
