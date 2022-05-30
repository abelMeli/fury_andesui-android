package com.mercadolibre.android.andesui.timepicker

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.facebook.common.logging.FLog
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.listener.RequestListener
import com.facebook.imagepipeline.listener.RequestLoggingListener
import com.facebook.soloader.SoLoader
import com.mercadolibre.android.andesui.timepicker.minutesInterval.AndesTimePickerInterval
import com.mercadolibre.android.andesui.timepicker.state.AndesTimePickerState
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
class AndesTimePickerTest {

    private lateinit var context: Context
    private lateinit var timePicker: AndesTimePicker

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
    }

    @Test
    fun `timePicker with all atributes`() {
        val label = "label"
        val helper = "helper"
        val currentTime = "00:00"
        val state = AndesTimePickerState.IDLE
        val type = AndesTimePickerType.TIME_INTERVAL
        val interval = AndesTimePickerInterval.MINUTES_30

        timePicker = AndesTimePicker(
            context,
            label,
            helper,
            currentTime,
            state,
            type,
            interval
        )

        Assert.assertEquals(label, timePicker.label)
        Assert.assertEquals(helper, timePicker.helper)
        Assert.assertEquals(currentTime, timePicker.currentTime)
        Assert.assertEquals(state, timePicker.state)
        Assert.assertEquals(type, timePicker.type)
        Assert.assertEquals(interval, timePicker.minutesInterval)
    }

    @Test
    fun `timePicker with all atributes by setter`() {
        val newLabel = "label"
        val newHelper = "helper"
        val newCurrentTime = "00:00"
        val newState = AndesTimePickerState.ERROR
        val newType = AndesTimePickerType.TIME_INTERVAL
        val newInterval = AndesTimePickerInterval.MINUTES_60

        timePicker = AndesTimePicker(
            context,
            null,
            null,
            null
        )

        timePicker.apply {
            label = newLabel
            helper = newHelper
            currentTime = newCurrentTime
            state = newState
            type = newType
            minutesInterval = newInterval
        }

        Assert.assertEquals(newLabel, timePicker.label)
        Assert.assertEquals(newHelper, timePicker.helper)
        Assert.assertEquals(newCurrentTime, timePicker.currentTime)
        Assert.assertEquals(newState, timePicker.state)
        Assert.assertEquals(newType, timePicker.type)
        Assert.assertEquals(newInterval, timePicker.minutesInterval)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `setter for callback throws exception with an invalid input`() {
        timePicker = AndesTimePicker(
            context,
            null,
            null,
            "invalid input"
        )
    }
}
