package com.mercadolibre.android.andesui.tooltip.location

import android.os.Build
import com.facebook.common.logging.FLog
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.listener.RequestListener
import com.facebook.imagepipeline.listener.RequestLoggingListener
import com.facebook.soloader.SoLoader
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.tooltip.factory.Constants.GENERIC_X_VALUE
import com.mercadolibre.android.andesui.tooltip.style.AndesTooltipSize
import com.nhaarman.mockitokotlin2.mock
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertFalse
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.spy
import org.mockito.Mockito.verify
import org.mockito.Mockito.validateMockitoUsage
import org.robolectric.ParameterizedRobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(ParameterizedRobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
class AndesTooltipLocationConfigTests(
    private val requiredLocation: AndesTooltipLocation,
    private val wantedConfig: AndesTooltipLocationConfig
) {
    private var context = RuntimeEnvironment.application
    private val tooltipMeasures = spy(AndesTooltipLocationInterfaceImplTest)
    companion object {
        @JvmStatic
        @ParameterizedRobolectricTestRunner.Parameters
        fun data(): Collection<Array<Any?>> {
            return listOf(
                    topLocationConfig,
                    bottomLocationConfig,
                    leftLocationConfig,
                    rightLocationConfig
            )
        }
    }
    @Before
    fun setUp() {
        SoLoader.setInTestMode()
        val requestListeners = setOf<RequestListener>(RequestLoggingListener())
        val config = ImagePipelineConfig.newBuilder(context)
                // other setters
                .setRequestListeners(requestListeners)
                .build()
        Fresco.initialize(context, config)
        FLog.setMinimumLoggingLevel(FLog.VERBOSE)
    }
    @After
    fun validate() {
        validateMockitoUsage()
    }
    @Test
    fun `should get required instance by passed AndesTooltipLocation`() {
        assertEquals(getAndesTooltipLocationConfig(tooltipMeasures, requiredLocation).javaClass, wantedConfig.javaClass)
    }
    @Test
    fun `should call tooltip showDropdown when buildTooltip`() {
        val tooltip: AndesTooltipLocationInterface = mock()
        val targetView = spy(AndesButton(context))

        `when`(tooltip.andesTooltipSize).thenReturn(AndesTooltipSize.DYNAMIC)

        val testedLocationConfig = getAndesTooltipLocationConfig(tooltip, requiredLocation)
        testedLocationConfig.buildTooltip(targetView)

        verify(tooltip).showDropDown(targetView, 0, 0, testedLocationConfig)
    }

    @Test
    fun `should call tooltip showDropdown when buildTooltip with FULL_SIZE`() {
        val tooltip: AndesTooltipLocationInterface = mock()
        val targetView = spy(AndesButton(context))

        `when`(tooltip.andesTooltipSize).thenReturn(AndesTooltipSize.FULL_SIZE)

        val testedLocationConfig = getAndesTooltipLocationConfig(tooltip, requiredLocation)
        testedLocationConfig.buildTooltip(targetView)

        verify(tooltip).showDropDown(targetView, 0, 0, testedLocationConfig)
    }

    @Test
    fun `should iterate other locations`() {
        val tooltip: AndesTooltipLocationInterface = mock()
        val targetView = spy(AndesButton(context))
        val locationSpy = spy(requiredLocation)

        `when`(tooltip.andesTooltipSize).thenReturn(AndesTooltipSize.DYNAMIC)

        val testedLocationConfig = getAndesTooltipLocationConfig(tooltip, locationSpy)
        assertFalse(testedLocationConfig.iterateOtherLocations(targetView))
    }

    @Test
    fun `should iterate other locations when FULL_SIZE`() {
        val tooltip: AndesTooltipLocationInterface = mock()
        val targetView = spy(AndesButton(context))
        val locationSpy = spy(requiredLocation)

        `when`(tooltip.andesTooltipSize).thenReturn(AndesTooltipSize.FULL_SIZE)

        val testedLocationConfig = getAndesTooltipLocationConfig(tooltip, locationSpy)
        assertFalse(testedLocationConfig.iterateOtherLocations(targetView))
    }

    @Test
    fun `should return arrowPoint`() {
        val tooltip: AndesTooltipLocationInterface = spy(AndesTooltipLocationInterfaceImplTest)
        val targetView = spy(AndesButton(context))

        val testedLocationConfig = getAndesTooltipLocationConfig(tooltip, requiredLocation)
        wantedConfig.buildTooltip(targetView)
        testedLocationConfig.buildTooltip(targetView)
        assertEquals(wantedConfig.getArrowPoint(GENERIC_X_VALUE.toInt()).x, testedLocationConfig.getArrowPoint(GENERIC_X_VALUE.toInt()).x)
        assertEquals(wantedConfig.getArrowPoint(GENERIC_X_VALUE.toInt()).y, testedLocationConfig.getArrowPoint(GENERIC_X_VALUE.toInt()).y)
    }
}
