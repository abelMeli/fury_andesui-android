package com.mercadolibre.android.andesui.tooltip.style

import android.view.View
import com.mercadolibre.android.andesui.tooltip.location.AndesTooltipArrowLocation
import com.mercadolibre.android.andesui.tooltip.location.AndesTooltipLocationInterfaceImplTest
import com.mercadolibre.android.andesui.tooltip.location.ArrowPositionId
import com.nhaarman.mockitokotlin2.spy
import junit.framework.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.robolectric.RuntimeEnvironment

class AndesTooltipSizeInterfaceTest {

    private val tooltipMeasures = spy(AndesTooltipLocationInterfaceImplTest)
    private val arrowLocation = mock(AndesTooltipArrowLocation::class.java)
    private val context = RuntimeEnvironment.application

    @Before
    fun setup() {
        setupMocks()
    }

    private fun setupMocks() {
        `when`(arrowLocation.getArrowPositionY(tooltipMeasures)).thenReturn(Y_VALUE)
    }

    @Test
    fun `when getArrowPoint in FULL_SIZE then return fixedValue`() {
        val returnedPoint = AndesTooltipSize.FULL_SIZE.type
            .getArrowPoint(arrowLocation, tooltipMeasures, FIXED_X_VALUE.toInt())

        assertEquals(returnedPoint.x, FIXED_X_VALUE)
    }

    @Test
    fun `when getTooltipXOffForSize in FULL_SIZE then return positionLeft`() {
        val mockView = spy(View(context))

        val returnedArrowData = AndesTooltipSize.FULL_SIZE.type.getTooltipXOffForSize(mockView, tooltipMeasures)
        val expectedPoint = tooltipMeasures.paddingWithArrow +
            tooltipMeasures.arrowWidth / 2 +
            tooltipMeasures.arrowBorder -
            tooltipMeasures.paddingWithArrow

        assertEquals(ArrowPositionId.LEFT, returnedArrowData.positionInSide)
        assertEquals(expectedPoint, returnedArrowData.point)
    }

    @Test
    fun `when get fromString then return proper one`() {
        val returnedFullSize = AndesTooltipSize.fromString("full_size")
        val returnedDynamic = AndesTooltipSize.fromString("dynamic")

        assertEquals(AndesTooltipSize.FULL_SIZE, returnedFullSize)
        assertEquals(AndesTooltipSize.DYNAMIC, returnedDynamic)
    }

    @Test
    fun `when get fromString then throw IllegalArgumentException`() {
        assertThrows(IllegalArgumentException::class.java) { AndesTooltipSize.fromString("other") }
    }

    companion object {
        private const val FIXED_X_VALUE = 1F
        private const val Y_VALUE = 123F
    }
}
