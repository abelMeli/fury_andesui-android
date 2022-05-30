package com.mercadolibre.android.andesui.timepicker

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.facebook.common.logging.FLog
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.listener.RequestListener
import com.facebook.imagepipeline.listener.RequestLoggingListener
import com.facebook.soloader.SoLoader
import com.mercadolibre.android.andesui.timepicker.minutesInterval.AndesTimePicker10MinutesInterval
import com.mercadolibre.android.andesui.timepicker.minutesInterval.AndesTimePicker15MinutesInterval
import com.mercadolibre.android.andesui.timepicker.minutesInterval.AndesTimePicker5MinutesInterval
import com.mercadolibre.android.andesui.timepicker.minutesInterval.AndesTimePicker30MinutesInterval
import com.mercadolibre.android.andesui.timepicker.minutesInterval.AndesTimePicker60MinutesInterval
import com.mercadolibre.android.andesui.timepicker.minutesInterval.AndesTimePickerInterval
import com.mercadolibre.android.andesui.timepicker.state.AndesDisabledTimePickerState
import com.mercadolibre.android.andesui.timepicker.state.AndesIdleTimePickerState
import com.mercadolibre.android.andesui.timepicker.state.AndesReadOnlyTimePickerState
import com.mercadolibre.android.andesui.timepicker.state.AndesTimePickerState
import com.mercadolibre.android.andesui.timepicker.state.AndesErrorTimePickerState
import com.mercadolibre.android.andesui.timepicker.type.AndesTimeIntervalTimePickerType
import com.mercadolibre.android.andesui.timepicker.type.AndesTimePickerType
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import org.junit.Assert
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
class AndesTimePickerInterfacesTest {

    private lateinit var context: Context
    private lateinit var andesTimePicker: AndesTimePicker

    companion object {
        @JvmStatic
        @BeforeClass
        fun beforeClass() {
            SoLoader.setInTestMode()
        }
    }

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        val requestListeners = setOf<RequestListener>(RequestLoggingListener())
        val config = ImagePipelineConfig.newBuilder(context)
                // other setters
                .setRequestListeners(requestListeners)
                .build()
        Fresco.initialize(context, config)
        FLog.setMinimumLoggingLevel(FLog.VERBOSE)

        andesTimePicker = AndesTimePicker(
                context = context,
                label = "Label",
                helper = "Helper",
                currentTime = "00:00"
        )
    }

    @Test
    fun `testing interface AndesTimePickerType`() {
        andesTimePicker.type = AndesTimePickerType.TIME_INTERVAL
        val type = andesTimePicker.type.type
        Assert.assertTrue(type is AndesTimeIntervalTimePickerType)
    }

    @Test
    fun `testing interface AndesTimePicker5MinutesInterval`() {
        andesTimePicker.minutesInterval = AndesTimePickerInterval.MINUTES_5
        val interval = andesTimePicker.minutesInterval.interval
        Assert.assertTrue(interval is AndesTimePicker5MinutesInterval)
    }

    @Test
    fun `testing interface AndesTimePicker10MinutesInterval`() {
        andesTimePicker.minutesInterval = AndesTimePickerInterval.MINUTES_10
        val interval = andesTimePicker.minutesInterval.interval
        Assert.assertTrue(interval is AndesTimePicker10MinutesInterval)
    }

    @Test
    fun `testing interface AndesTimePicker15MinutesInterval`() {
        andesTimePicker.minutesInterval = AndesTimePickerInterval.MINUTES_15
        val interval = andesTimePicker.minutesInterval.interval
        Assert.assertTrue(interval is AndesTimePicker15MinutesInterval)
    }

    @Test
    fun `testing interface AndesTimePicker30MinutesInterval`() {
        andesTimePicker.minutesInterval = AndesTimePickerInterval.MINUTES_30
        val interval = andesTimePicker.minutesInterval.interval
        Assert.assertTrue(interval is AndesTimePicker30MinutesInterval)
    }

    @Test
    fun `testing interface AndesTimePicker60MinutesInterval`() {
        andesTimePicker.minutesInterval = AndesTimePickerInterval.MINUTES_60
        val interval = andesTimePicker.minutesInterval.interval
        Assert.assertTrue(interval is AndesTimePicker60MinutesInterval)
    }

    @Test
    fun `testing interface AndesIdleTimePickerState`() {
        andesTimePicker.state = AndesTimePickerState.IDLE
        val interval = andesTimePicker.state.state
        Assert.assertTrue(interval is AndesIdleTimePickerState)
    }

    @Test
    fun `testing interface AndesDisabledTimePickerState`() {
        andesTimePicker.state = AndesTimePickerState.DISABLED
        val interval = andesTimePicker.state.state
        Assert.assertTrue(interval is AndesDisabledTimePickerState)
    }

    @Test
    fun `testing interface AndesReadOnlyTimePickerState`() {
        andesTimePicker.state = AndesTimePickerState.READONLY
        val interval = andesTimePicker.state.state
        Assert.assertTrue(interval is AndesReadOnlyTimePickerState)
    }

    @Test
    fun `testing interface AndesErrorTimePickerState`() {
        andesTimePicker.state = AndesTimePickerState.ERROR
        val interval = andesTimePicker.state.state
        Assert.assertTrue(interval is AndesErrorTimePickerState)
    }
}
