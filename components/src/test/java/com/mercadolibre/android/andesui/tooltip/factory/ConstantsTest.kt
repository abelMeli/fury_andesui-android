package com.mercadolibre.android.andesui.tooltip.factory

import junit.framework.Assert.assertEquals
import org.junit.Test

class ConstantsTest {

    private val tooltipConstants = Constants

    @Test
    fun `when get Generic X Value then return 0F`() {
        assertEquals(0F, tooltipConstants.GENERIC_X_VALUE)
    }
}
