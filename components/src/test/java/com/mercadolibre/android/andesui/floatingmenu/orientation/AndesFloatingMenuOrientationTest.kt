package com.mercadolibre.android.andesui.floatingmenu.orientation

import org.junit.Test

class AndesFloatingMenuOrientationTest {
    @Test
    fun `AndesFloatingMenuOrientation for Left string`() {
        val orientationFromString = AndesFloatingMenuOrientation.fromString("Left")
        assert(orientationFromString is AndesFloatingMenuOrientation.Left)
    }

    @Test
    fun `AndesFloatingMenuOrientation for Right string`() {
        val orientationFromString = AndesFloatingMenuOrientation.fromString("Right")
        assert(orientationFromString is AndesFloatingMenuOrientation.Right)
    }

    @Test
    fun `AndesFloatingMenuRow returns Left when string is not any sealed class`() {
        val orientationFromString = AndesFloatingMenuOrientation.fromString("Other")
        assert(orientationFromString is AndesFloatingMenuOrientation.Left)
    }
}
