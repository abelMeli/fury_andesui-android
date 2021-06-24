package com.mercadolibre.android.andesui.switch

import android.os.Build
import android.view.MotionEvent
import android.view.View
import androidx.test.core.view.MotionEventBuilder
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.switch.align.AndesSwitchAlign
import com.mercadolibre.android.andesui.switch.factory.AndesSwitchAttrs
import com.mercadolibre.android.andesui.switch.factory.AndesSwitchComponentResourcesProvider
import com.mercadolibre.android.andesui.switch.factory.AndesSwitchConfigurationFactory
import com.mercadolibre.android.andesui.switch.status.AndesSwitchStatus
import com.mercadolibre.android.andesui.switch.type.AndesSwitchType
import com.nhaarman.mockitokotlin2.mock
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.spy
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
class AndesSwitchTest {

    private var context = RuntimeEnvironment.application
    private val configFactory = spy(AndesSwitchConfigurationFactory)
    private lateinit var attrs: AndesSwitchAttrs
    private lateinit var andesSwitch: AndesSwitch

    @Test
    fun `Switch, Left`() {
        attrs = AndesSwitchAttrs(
            andesSwitchAlign = AndesSwitchAlign.LEFT,
            andesSwitchStatus = AndesSwitchStatus.UNCHECKED,
            andesSwitchType = AndesSwitchType.ENABLED,
            andesSwitchText = "Andes Switch"
        )
        val config = configFactory.create(attrs)
        Assert.assertEquals(config.leftTextVisibility, View.GONE)
        Assert.assertEquals(config.rightTextVisibility, View.VISIBLE)
    }

    @Test
    fun `Switch, Right`() {
        attrs = AndesSwitchAttrs(
            andesSwitchAlign = AndesSwitchAlign.RIGHT,
            andesSwitchStatus = AndesSwitchStatus.UNCHECKED,
            andesSwitchType = AndesSwitchType.ENABLED,
            andesSwitchText = "Andes Switch"
        )
        val config = configFactory.create(attrs)
        Assert.assertEquals(config.leftTextVisibility, View.VISIBLE)
        Assert.assertEquals(config.rightTextVisibility, View.GONE)
    }

    @Test
    fun `Switch, Enabled`() {
        attrs = AndesSwitchAttrs(
            andesSwitchAlign = AndesSwitchAlign.RIGHT,
            andesSwitchStatus = AndesSwitchStatus.UNCHECKED,
            andesSwitchType = AndesSwitchType.ENABLED,
            andesSwitchText = "Andes Switch"
        )
        val config = configFactory.create(attrs)
        Assert.assertEquals(attrs.andesSwitchType, config.type)
    }

    @Test
    fun `Switch, Disabled`() {
        attrs = AndesSwitchAttrs(
            andesSwitchAlign = AndesSwitchAlign.RIGHT,
            andesSwitchStatus = AndesSwitchStatus.UNCHECKED,
            andesSwitchType = AndesSwitchType.DISABLED,
            andesSwitchText = "Andes Switch"
        )
        val config = configFactory.create(attrs)
        Assert.assertEquals(attrs.andesSwitchType, config.type)
    }

    @Test
    fun `Switch, Checked`() {
        attrs = AndesSwitchAttrs(
            andesSwitchAlign = AndesSwitchAlign.RIGHT,
            andesSwitchStatus = AndesSwitchStatus.CHECKED,
            andesSwitchType = AndesSwitchType.ENABLED,
            andesSwitchText = "Andes Switch"
        )
        val config = configFactory.create(attrs)
        Assert.assertEquals(attrs.andesSwitchType, config.type)
    }

    @Test
    fun `Switch, Unchecked`() {
        attrs = AndesSwitchAttrs(
            andesSwitchAlign = AndesSwitchAlign.RIGHT,
            andesSwitchStatus = AndesSwitchStatus.UNCHECKED,
            andesSwitchType = AndesSwitchType.ENABLED,
            andesSwitchText = "Andes Switch"
        )
        val config = configFactory.create(attrs)
        Assert.assertEquals(attrs.andesSwitchType, config.type)
    }

    @Test
    fun `Switch, Unchecked, Enabled, Colors`() {
        val switchComponent = AndesSwitchComponent(context, null)
        val status = AndesSwitchStatus.UNCHECKED
        val type = AndesSwitchType.ENABLED
        val provider = AndesSwitchComponentResourcesProvider

        switchComponent.setupSwitch(
            status = status,
            type = type
        )

        Assert.assertEquals(
            provider.createTrackBackgroundColor(status, type, context),
            context.resources.getColor(R.color.andes_gray_100)
        )
    }

    @Test
    fun `Switch, Unchecked, Disabled, Colors`() {
        val switchComponent = AndesSwitchComponent(context, null)
        val status = AndesSwitchStatus.UNCHECKED
        val type = AndesSwitchType.DISABLED
        val provider = AndesSwitchComponentResourcesProvider

        switchComponent.setupSwitch(
            status = status,
            type = type
        )

        Assert.assertEquals(
            provider.createTrackBackgroundColor(status, type, context),
            context.resources.getColor(R.color.andes_gray_070)
        )
    }

    @Test
    fun `Switch, Checked, Enabled, Colors`() {
        val switchComponent = AndesSwitchComponent(context, null)
        val status = AndesSwitchStatus.CHECKED
        val type = AndesSwitchType.ENABLED
        val provider = AndesSwitchComponentResourcesProvider

        switchComponent.setupSwitch(
            status = status,
            type = type
        )

        Assert.assertEquals(
            provider.createTrackBackgroundColor(status, type, context),
            context.resources.getColor(R.color.andes_accent_color_500)
        )
    }

    @Test
    fun `Switch, Checked, Disabled, Colors`() {
        val switchComponent = AndesSwitchComponent(context, null)
        val status = AndesSwitchStatus.CHECKED
        val type = AndesSwitchType.DISABLED
        val provider = AndesSwitchComponentResourcesProvider

        switchComponent.setupSwitch(
            status = status,
            type = type
        )

        Assert.assertEquals(
            provider.createTrackBackgroundColor(status, type, context),
            context.resources.getColor(R.color.andes_accent_color_300)
        )
    }

    @Test
    fun `Switch, Checked, Disabled2`() {
        val switchComponent = AndesSwitchComponent(context, null)
        val status = AndesSwitchStatus.UNCHECKED
        val type = AndesSwitchType.ENABLED
        val provider = AndesSwitchComponentResourcesProvider

        switchComponent.setupSwitch(
            status = status,
            type = type
        )

        Assert.assertEquals(
            provider.createTrackBackgroundColor(status, type, context),
            context.resources.getColor(R.color.andes_gray_100)
        )
    }

    @Test
    fun `testing multiline getter and setter`() {
        andesSwitch = AndesSwitch(context)
        andesSwitch.titleNumberOfLines = 2
        Assert.assertEquals(2, andesSwitch.titleNumberOfLines)
        Assert.assertEquals(2, andesSwitch.leftTextComponent.maxLines)
        Assert.assertEquals(2, andesSwitch.rightTextComponent.maxLines)
    }

    @Test
    fun `setCallback method works correctly`() {
        andesSwitch = AndesSwitch(context)
        val mockedListener: AndesSwitch.OnStatusChangeListener = mock()
        andesSwitch.setOnStatusChangeListener(mockedListener)
        Assert.assertNotNull(andesSwitch.onStatusChangeListener)
    }

    @Test
    fun `onInterceptTouchEvent works correctly with action_down`() {
        andesSwitch = AndesSwitch(context)
        val touchEvent = MotionEventBuilder.newBuilder().setAction(MotionEvent.ACTION_DOWN).build()

        val eventReturn = andesSwitch.onInterceptTouchEvent(touchEvent)

        Assert.assertFalse(eventReturn)
    }

    @Test
    fun `onInterceptTouchEvent works correctly with action_move`() {
        andesSwitch = AndesSwitch(context)
        val touchEvent = MotionEventBuilder.newBuilder().setAction(MotionEvent.ACTION_MOVE).build()

        val eventReturn = andesSwitch.onInterceptTouchEvent(touchEvent)

        Assert.assertFalse(eventReturn)
    }

    @Test
    fun `onInterceptTouchEvent works correctly with null event`() {
        andesSwitch = AndesSwitch(context)

        val eventReturn = andesSwitch.onInterceptTouchEvent(null)

        Assert.assertFalse(eventReturn)
    }
}
