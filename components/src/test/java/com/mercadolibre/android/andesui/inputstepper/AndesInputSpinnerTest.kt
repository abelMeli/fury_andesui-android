package com.mercadolibre.android.andesui.inputstepper

import android.content.Context
import android.widget.TextView
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.inputstepper.accessibility.AndesInputStepperAccessibilityEventDispatcher
import com.mercadolibre.android.andesui.inputstepper.enabledstate.AndesInputStepperEnabledState
import com.mercadolibre.android.andesui.inputstepper.size.AndesInputStepperSize
import com.mercadolibre.android.andesui.inputstepper.status.AndesInputStepperStatus
import com.mercadolibre.android.andesui.utils.Constants
import com.nhaarman.mockitokotlin2.eq
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import org.robolectric.util.ReflectionHelpers


@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Constants.TEST_ANDROID_VERSION_CODE])
@LooperMode(LooperMode.Mode.PAUSED)
internal class AndesInputStepperTest {

    private lateinit var context: Context
    private lateinit var inputStepper: AndesInputStepper

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        inputStepper = Mockito.spy(AndesInputStepper(
            context,
            size = AndesInputStepperSize.Large,
            step = 1,
            maxValue = 10,
            minValue = 0,
            value = 5,
            status = AndesInputStepperStatus.Enabled
        ))
    }

    @Test
    fun `validate build component from xml with default values`() {
        val attrs = Robolectric.buildAttributeSet()
            .build()

        inputStepper = AndesInputStepper(context, attrs)

        Assert.assertEquals(0, inputStepper.value)
        Assert.assertEquals(Int.MAX_VALUE, inputStepper.maxValue)
        Assert.assertEquals(0, inputStepper.minValue)
        Assert.assertEquals(1, inputStepper.step)
        Assert.assertEquals(AndesInputStepperSize.Large, inputStepper.size)
        Assert.assertEquals(AndesInputStepperStatus.Enabled, inputStepper.status)
    }

    @Test
    fun `validate build component from xml with specific values`() {
        val step = 3
        val minValue = -3
        val maxValue = 9
        val value = 6
        val size = Pair(AndesInputStepperSize.Small, "1001")
        val status = Pair(AndesInputStepperStatus.Loading, "2002")

        val attrs = Robolectric.buildAttributeSet()
            .addAttribute(R.attr.andesInputStepperMaxValue, maxValue.toString())
            .addAttribute(R.attr.andesInputStepperStep, step.toString())
            .addAttribute(R.attr.andesInputStepperMinValue, minValue.toString())
            .addAttribute(R.attr.andesInputStepperStatus, status.second)
            .addAttribute(R.attr.andesInputStepperValue, value.toString())
            .addAttribute(R.attr.andesInputStepperSize, size.second)
            .build()
        inputStepper = AndesInputStepper(context, attrs)

        Assert.assertEquals(value, inputStepper.value)
        Assert.assertEquals(maxValue, inputStepper.maxValue)
        Assert.assertEquals(minValue, inputStepper.minValue)
        Assert.assertEquals(step, inputStepper.step)
        Assert.assertEquals(size.first, inputStepper.size)
        Assert.assertEquals(status.first, inputStepper.status)
    }

    @Test
    fun `validate build component from xml with specific values and style`() {
        val step = 2
        val minValue = -10
        val maxValue = 10
        val value = 5
        val size = Pair(AndesInputStepperSize.Small, "1001")
        val status = Pair(AndesInputStepperStatus.Disabled, "2001")

        val attrs = Robolectric.buildAttributeSet()
            .addAttribute(R.attr.andesInputStepperMaxValue, maxValue.toString())
            .addAttribute(R.attr.andesInputStepperStep, step.toString())
            .addAttribute(R.attr.andesInputStepperMinValue, minValue.toString())
            .addAttribute(R.attr.andesInputStepperStatus, status.second)
            .addAttribute(R.attr.andesInputStepperValue, value.toString())
            .addAttribute(R.attr.andesInputStepperSize, size.second)
            .build()
        inputStepper = AndesInputStepper(context, attrs, R.style.Andes)

        Assert.assertEquals(value, inputStepper.value)
        Assert.assertEquals(maxValue, inputStepper.maxValue)
        Assert.assertEquals(minValue, inputStepper.minValue)
        Assert.assertEquals(step, inputStepper.step)
        Assert.assertEquals(size.first, inputStepper.size)
        Assert.assertEquals(status.first, inputStepper.status)
    }

    @Test
    fun `validate set value in range`() {
        val expected = "1"
        inputStepper.apply {
            maxValue = 10
            minValue = 0
            setValue(5)
        }

        inputStepper.setValue(1)

        Assert.assertEquals(expected, inputStepper.getText())
    }


    @Test
    fun `validate set value when is extra limited`() {
        val expected = "10"
        inputStepper.apply {
            maxValue = 10
            setValue(5)
        }

        inputStepper.setValue(20)

        Assert.assertEquals(expected, inputStepper.getText())
    }

    @Test
    fun `validate set value when is over limited`() {
        val expected = "5"
        inputStepper.apply {
            minValue = 5
            setValue(10)
        }

        inputStepper.setValue(1)

        Assert.assertEquals(expected, inputStepper.getText())
    }

    @Test
    fun `validate perform event next`() {
        val expected = "2"
        inputStepper.apply {
            step = 2
            setValue(0)
        }

        inputStepper.performEvent(AndesInputStepperEvent.NEXT)

        Assert.assertEquals(expected, inputStepper.getText())
    }

    @Test
    fun `validate perform event previous`() {
        val expected = "0"
        inputStepper.apply {
            step = 2
            setValue(0)
        }

        inputStepper.performEvent(AndesInputStepperEvent.PREVIOUS)

        Assert.assertEquals(expected, inputStepper.getText())
    }

    @Test
    fun `validate call listener`() {
        val listener = Mockito.mock(AndesInputStepperValueListener::class.java)
        inputStepper.apply {
            step = 2
            setValue(0)
        }

        inputStepper.valueListener = listener
        inputStepper.performEvent(AndesInputStepperEvent.NEXT)

        Mockito.verify(listener).onValueSelected(AndesInputStepperEvent.NEXT, 2, AndesInputStepperEnabledState.InRange)
    }


    @Test
    fun `validate call datasource`() {
        val expected = "custom string"
        val dataSource = Mockito.mock(AndesInputStepperTextDataSource::class.java)
        Mockito.`when`(dataSource.getText(2)).thenReturn(expected)
        inputStepper.apply {
            step = 2
            setValue(0)
        }

        inputStepper.dataSource = dataSource
        inputStepper.performEvent(AndesInputStepperEvent.NEXT)

        Mockito.verify(dataSource).getText(2)
        Assert.assertEquals(expected, inputStepper.getText())
    }

    @Test
    fun `validate call a11yEvent`() {
        val dispatcher = Mockito.mock(AndesInputStepperAccessibilityEventDispatcher::class.java)
        ReflectionHelpers.setField(inputStepper, "a11yEventDispatcher", dispatcher)
        inputStepper.apply {
            step = 2
            setValue(0)
        }

        inputStepper.performEvent(AndesInputStepperEvent.NEXT)

        Mockito.verify(dispatcher).notifyA11ySpinnerSetValue(any(TextView::class.java), eq("2"))
    }

    private fun <T> any(type: Class<T>): T = Mockito.any<T>(type)

}