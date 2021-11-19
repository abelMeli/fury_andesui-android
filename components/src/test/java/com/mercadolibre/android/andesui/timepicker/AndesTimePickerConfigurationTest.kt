package com.mercadolibre.android.andesui.timepicker

import android.os.Build
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.timepicker.factory.AndesTimePickerAttrs
import com.mercadolibre.android.andesui.timepicker.factory.AndesTimePickerConfigurationFactory
import com.mercadolibre.android.andesui.timepicker.minutesInterval.AndesTimePickerInterval
import com.mercadolibre.android.andesui.timepicker.state.AndesTimePickerState
import com.mercadolibre.android.andesui.timepicker.type.AndesTimePickerType
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.spy
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
class AndesTimePickerConfigurationTest {

    private var context = RuntimeEnvironment.application
    private val configFactory = spy(AndesTimePickerConfigurationFactory)
    private lateinit var attrs: AndesTimePickerAttrs

    @Test
    fun `State idle, interval_30, time_line`() {
        // Given
        attrs = AndesTimePickerAttrs(
            andesTimePickerLabel = "Label",
            andesTimePickerHelper = "Helper",
            andesTimePickerCurrentTime = "00:00",
            andesTimePickerState = AndesTimePickerState.IDLE,
            andesTimePickerType = AndesTimePickerType.TIME_INTERVAL,
            andesTimePickerInterval = AndesTimePickerInterval.MINUTES_30
        )

        // When
        val config = configFactory.create(attrs)

        // Then
        assertEquals(config.helper, attrs.andesTimePickerHelper)
        assertEquals(config.label, attrs.andesTimePickerLabel)
        assertEquals(config.iconColor, R.color.andes_accent_color_500)
        assertEquals(config.interval::class.java, AndesTimePickerInterval.MINUTES_30.interval::class.java)
        assertEquals(config.state, AndesTimePickerState.IDLE)
    }

    @Test
    fun `State idle, interval_60, time_line`() {
        // Given
        attrs = AndesTimePickerAttrs(
                andesTimePickerLabel = "Label",
                andesTimePickerHelper = "Helper",
                andesTimePickerCurrentTime = "00:00",
                andesTimePickerState = AndesTimePickerState.IDLE,
                andesTimePickerType = AndesTimePickerType.TIME_INTERVAL,
                andesTimePickerInterval = AndesTimePickerInterval.MINUTES_60
        )

        // When
        val config = configFactory.create(attrs)

        // Then
        assertEquals(config.helper, attrs.andesTimePickerHelper)
        assertEquals(config.label, attrs.andesTimePickerLabel)
        assertEquals(config.iconColor, R.color.andes_accent_color_500)
        assertEquals(config.interval::class.java, AndesTimePickerInterval.MINUTES_60.interval::class.java)
        assertEquals(config.state, AndesTimePickerState.IDLE)
        assertEquals(config.type, AndesTimePickerType.TIME_INTERVAL)
    }

    @Test
    fun `State error, interval_30, time_line`() {
        // Given
        attrs = AndesTimePickerAttrs(
                andesTimePickerLabel = "Label",
                andesTimePickerHelper = "Helper",
                andesTimePickerCurrentTime = "00:00",
                andesTimePickerState = AndesTimePickerState.ERROR,
                andesTimePickerType = AndesTimePickerType.TIME_INTERVAL,
                andesTimePickerInterval = AndesTimePickerInterval.MINUTES_30
        )

        // When
        val config = configFactory.create(attrs)

        // Then
        assertEquals(config.helper, attrs.andesTimePickerHelper)
        assertEquals(config.label, attrs.andesTimePickerLabel)
        assertEquals(config.iconColor, R.color.andes_accent_color_500)
        assertEquals(config.interval::class.java, AndesTimePickerInterval.MINUTES_30.interval::class.java)
        assertEquals(config.state, AndesTimePickerState.ERROR)
        assertEquals(config.type, AndesTimePickerType.TIME_INTERVAL)
    }

    @Test
    fun `State error, interval_60, time_line`() {
        // Given
        attrs = AndesTimePickerAttrs(
                andesTimePickerLabel = "Label",
                andesTimePickerHelper = "Helper",
                andesTimePickerCurrentTime = "00:00",
                andesTimePickerState = AndesTimePickerState.ERROR,
                andesTimePickerType = AndesTimePickerType.TIME_INTERVAL,
                andesTimePickerInterval = AndesTimePickerInterval.MINUTES_60
        )

        // When
        val config = configFactory.create(attrs)

        // Then
        assertEquals(config.helper, attrs.andesTimePickerHelper)
        assertEquals(config.label, attrs.andesTimePickerLabel)
        assertEquals(config.iconColor, R.color.andes_accent_color_500)
        assertEquals(config.interval::class.java, AndesTimePickerInterval.MINUTES_60.interval::class.java)
        assertEquals(config.state, AndesTimePickerState.ERROR)
        assertEquals(config.type, AndesTimePickerType.TIME_INTERVAL)
    }

    @Test
    fun `State read only, interval_30, time_line`() {
        // Given
        attrs = AndesTimePickerAttrs(
                andesTimePickerLabel = "Label",
                andesTimePickerHelper = "Helper",
                andesTimePickerCurrentTime = "00:00",
                andesTimePickerState = AndesTimePickerState.READONLY,
                andesTimePickerType = AndesTimePickerType.TIME_INTERVAL,
                andesTimePickerInterval = AndesTimePickerInterval.MINUTES_30
        )

        // When
        val config = configFactory.create(attrs)

        // Then
        assertEquals(config.helper, attrs.andesTimePickerHelper)
        assertEquals(config.label, attrs.andesTimePickerLabel)
        assertEquals(config.iconColor, R.color.andes_accent_color_500)
        assertEquals(config.interval::class.java, AndesTimePickerInterval.MINUTES_30.interval::class.java)
        assertEquals(config.state, AndesTimePickerState.READONLY)
        assertEquals(config.type, AndesTimePickerType.TIME_INTERVAL)
    }

    @Test
    fun `State read only, interval_60, time_line`() {
        // Given
        attrs = AndesTimePickerAttrs(
                andesTimePickerLabel = "Label",
                andesTimePickerHelper = "Helper",
                andesTimePickerCurrentTime = "00:00",
                andesTimePickerState = AndesTimePickerState.READONLY,
                andesTimePickerType = AndesTimePickerType.TIME_INTERVAL,
                andesTimePickerInterval = AndesTimePickerInterval.MINUTES_60
        )

        // When
        val config = configFactory.create(attrs)

        // Then
        assertEquals(config.helper, attrs.andesTimePickerHelper)
        assertEquals(config.label, attrs.andesTimePickerLabel)
        assertEquals(config.iconColor, R.color.andes_accent_color_500)
        assertEquals(config.interval::class.java, AndesTimePickerInterval.MINUTES_60.interval::class.java)
        assertEquals(config.state, AndesTimePickerState.READONLY)
        assertEquals(config.type, AndesTimePickerType.TIME_INTERVAL)
    }

    @Test
    fun `State disabled, interval_30, time_line`() {
        // Given
        attrs = AndesTimePickerAttrs(
                andesTimePickerLabel = "Label",
                andesTimePickerHelper = "Helper",
                andesTimePickerCurrentTime = "00:00",
                andesTimePickerState = AndesTimePickerState.DISABLED,
                andesTimePickerType = AndesTimePickerType.TIME_INTERVAL,
                andesTimePickerInterval = AndesTimePickerInterval.MINUTES_30
        )

        // When
        val config = configFactory.create(attrs)

        // Then
        assertEquals(config.helper, attrs.andesTimePickerHelper)
        assertEquals(config.label, attrs.andesTimePickerLabel)
        assertEquals(config.iconColor, R.color.andes_accent_color_500)
        assertEquals(config.interval::class.java, AndesTimePickerInterval.MINUTES_30.interval::class.java)
        assertEquals(config.state, AndesTimePickerState.DISABLED)
        assertEquals(config.type, AndesTimePickerType.TIME_INTERVAL)
    }

    @Test
    fun `State disabled, interval_60, time_line`() {
        // Given
        attrs = AndesTimePickerAttrs(
                andesTimePickerLabel = "Label",
                andesTimePickerHelper = "Helper",
                andesTimePickerCurrentTime = "00:00",
                andesTimePickerState = AndesTimePickerState.DISABLED,
                andesTimePickerType = AndesTimePickerType.TIME_INTERVAL,
                andesTimePickerInterval = AndesTimePickerInterval.MINUTES_60
        )

        // When
        val config = configFactory.create(attrs)

        // Then
        assertEquals(config.helper, attrs.andesTimePickerHelper)
        assertEquals(config.label, attrs.andesTimePickerLabel)
        assertEquals(config.iconColor, R.color.andes_accent_color_500)
        assertEquals(config.interval::class.java, AndesTimePickerInterval.MINUTES_60.interval::class.java)
        assertEquals(config.state, AndesTimePickerState.DISABLED)
        assertEquals(config.type, AndesTimePickerType.TIME_INTERVAL)
    }
}
