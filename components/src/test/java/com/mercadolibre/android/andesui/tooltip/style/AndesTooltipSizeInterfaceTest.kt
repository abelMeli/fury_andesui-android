package com.mercadolibre.android.andesui.tooltip.style

import android.view.View
import com.mercadolibre.android.andesui.tooltip.location.AndesTooltipArrowLocation
import com.mercadolibre.android.andesui.tooltip.location.AndesTooltipLocationInterfaceImplTest
import com.mercadolibre.android.andesui.tooltip.location.AndesTooltipLocationInterfaceImplTestFullsize
import com.mercadolibre.android.andesui.tooltip.location.ArrowPositionId
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import com.mercadolibre.android.andesui.utils.ScreenUtils
import com.nhaarman.mockitokotlin2.spy
import io.mockk.every
import io.mockk.mockkObject
import junit.framework.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE], qualifiers = "w720dp-h1600dp-mdpi")
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
    fun `when getArrowPoint in FULL_SIZE then return x Value`() {
        val returnedPoint = AndesTooltipSize.FULL_SIZE.type
            .getArrowPoint(arrowLocation, tooltipMeasures, FIXED_X_VALUE.toInt())

        assertEquals(returnedPoint.x, arrowLocation.getArrowPositionX(tooltipMeasures))
    }

    @Test
    fun `when getArrowPoint in FULL_SIZE then return fixedValue`() {
        //GIVE
        val tooltipMeasures = spy(AndesTooltipLocationInterfaceImplTestFullsize)

        //WHEN
        val returnedPoint = AndesTooltipSize.FULL_SIZE.type
            .getArrowPoint(arrowLocation, tooltipMeasures, FIXED_X_VALUE.toInt())

        //THEN
        assertEquals(returnedPoint.x, FIXED_X_VALUE)
    }

    @Test
    fun `when getTooltipXOffForSize in FULL_SIZE then return positionLeft`() {
        //GIVE
        val tooltipMeasures = spy(AndesTooltipLocationInterfaceImplTestFullsize)
        val mockView = spy(View(context))

        //WHEN
        val returnedArrowData = AndesTooltipSize.FULL_SIZE.type.getTooltipXOffForSize(mockView, tooltipMeasures)
        val expectedPoint = tooltipMeasures.paddingWithArrowHorizontal +
            tooltipMeasures.arrowWidth / 2 +
            tooltipMeasures.arrowBorder -
            tooltipMeasures.paddingWithArrowHorizontal

        //THEN
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
