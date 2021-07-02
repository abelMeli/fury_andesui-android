package com.mercadolibre.android.andesui.floatingmenu.rows
import org.junit.Test

class AndesFloatingMenuRowsTest {
    @Test
    fun `AndesFloatingMenuRow for Small string`() {
        val rowFromString = AndesFloatingMenuRows.fromString("Small")
        assert(rowFromString is AndesFloatingMenuRows.Small)
    }

    @Test
    fun `AndesFloatingMenuRow for Medium string`() {
        val rowFromString = AndesFloatingMenuRows.fromString("Medium")
        assert(rowFromString is AndesFloatingMenuRows.Medium)
    }

    @Test
    fun `AndesFloatingMenuRow for Max string`() {
        val rowFromString = AndesFloatingMenuRows.fromString("Max")
        assert(rowFromString is AndesFloatingMenuRows.Max)
    }

    @Test
    fun `AndesFloatingMenuRow returns Medium when string is not any sealed class`() {
        val rowFromString = AndesFloatingMenuRows.fromString("Other")
        assert(rowFromString is AndesFloatingMenuRows.Medium)
    }
}
