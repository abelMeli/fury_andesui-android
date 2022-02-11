package com.mercadolibre.android.andesui.checkbox

import com.mercadolibre.android.andesui.assertEquals
import com.mercadolibre.android.andesui.checkbox.align.AndesCheckboxAlign
import com.mercadolibre.android.andesui.checkbox.status.AndesCheckboxStatus
import com.mercadolibre.android.andesui.checkbox.type.AndesCheckboxType
import java.lang.IllegalArgumentException
import org.junit.Assert
import org.junit.Test

class AndesCheckboxEnumsTest {

    @Test
    fun `AndesCheckboxStatus fromString parse valid`() {
        // WHEN
        val selected = AndesCheckboxStatus.fromString("selected")
        val unselected = AndesCheckboxStatus.fromString("unselected")
        val undefined = AndesCheckboxStatus.fromString("undefined")

        // THEN
        selected assertEquals AndesCheckboxStatus.SELECTED
        unselected assertEquals AndesCheckboxStatus.UNSELECTED
        undefined assertEquals AndesCheckboxStatus.UNDEFINED
    }

    @Test
    fun `AndesCheckboxStatus fromString parse invalid`() {
        // WHEN - THEN
        Assert.assertThrows(IllegalArgumentException::class.java) {
            AndesCheckboxStatus.fromString("select")
        }
    }

    @Test
    fun `AndesCheckboxType fromString parse valid`() {
        // WHEN
        val idle = AndesCheckboxType.fromString("idle")
        val disabled = AndesCheckboxType.fromString("disabled")
        val error = AndesCheckboxType.fromString("error")

        // THEN
        idle assertEquals AndesCheckboxType.IDLE
        disabled assertEquals AndesCheckboxType.DISABLED
        error assertEquals AndesCheckboxType.ERROR
    }

    @Test
    fun `AndesCheckboxType fromString parse invalid`() {
        // WHEN - THEN
        Assert.assertThrows(IllegalArgumentException::class.java) {
            AndesCheckboxType.fromString("medium")
        }
    }

    @Test
    fun `AndesCheckboxAlign fromString parse valid`() {
        // WHEN
        val left = AndesCheckboxAlign.fromString("left")
        val right = AndesCheckboxAlign.fromString("right")

        // THEN
        left assertEquals AndesCheckboxAlign.LEFT
        right assertEquals AndesCheckboxAlign.RIGHT
    }

    @Test
    fun `AndesTagLeftContent fromString parse invalid`() {
        // WHEN - THEN
        Assert.assertThrows(IllegalArgumentException::class.java) {
            AndesCheckboxAlign.fromString("center")
        }
    }
}
