package com.mercadolibre.android.andesui.checkbox

import android.view.MotionEvent
import androidx.test.core.view.MotionEventBuilder
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.utils.activateTalkbackForTest
import com.mercadolibre.android.andesui.utils.assertEquals
import com.mercadolibre.android.andesui.checkbox.align.AndesCheckboxAlign
import com.mercadolibre.android.andesui.checkbox.factory.AndesCheckboxAttrs
import com.mercadolibre.android.andesui.checkbox.factory.AndesCheckboxConfigurationFactory
import com.mercadolibre.android.andesui.checkbox.status.AndesCheckboxStatus
import com.mercadolibre.android.andesui.checkbox.type.AndesCheckboxType
import com.mercadolibre.android.andesui.color.toAndesColor
import com.mercadolibre.android.andesui.utils.getClickableSpans
import com.mercadolibre.android.andesui.message.bodylinks.AndesBodyLink
import com.mercadolibre.android.andesui.message.bodylinks.AndesBodyLinks
import com.mercadolibre.android.andesui.textview.AndesTextView
import com.mercadolibre.android.andesui.textview.color.AndesTextViewColor
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertFalse
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.spy
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
class AndesCheckboxTest {

    private var context = RuntimeEnvironment.application
    private val configFactory = spy(AndesCheckboxConfigurationFactory)
    private lateinit var attrs: AndesCheckboxAttrs
    private lateinit var andesCheckbox: AndesCheckbox

    @Test
    fun `Checkbox, Idle, Unselected, Border`() {
        attrs = AndesCheckboxAttrs(
            andesCheckboxAlign = AndesCheckboxAlign.LEFT,
            andesCheckboxStatus = AndesCheckboxStatus.UNSELECTED,
            andesCheckboxType = AndesCheckboxType.IDLE,
            andesCheckboxText = "Andes checkbox"
        )
        val config = configFactory.create(attrs)
        assertEquals(
                config.type.type.borderColor(context, config.status),
                R.color.andes_gray_250_solid.toAndesColor()
        )
    }

    @Test
    fun `Checkbox, Idle, Unselected, Background`() {
        attrs = AndesCheckboxAttrs(
                andesCheckboxAlign = AndesCheckboxAlign.LEFT,
                andesCheckboxStatus = AndesCheckboxStatus.UNSELECTED,
                andesCheckboxType = AndesCheckboxType.IDLE,
                andesCheckboxText = "Andes checkbox"
        )
        val config = configFactory.create(attrs)
        assertEquals(
                config.type.type.backgroundColor(context, config.status),
                R.color.andes_white.toAndesColor()
        )
    }

    @Test
    fun `Checkbox, Idle, Unselected, Icon color`() {
        attrs = AndesCheckboxAttrs(
                andesCheckboxAlign = AndesCheckboxAlign.LEFT,
                andesCheckboxStatus = AndesCheckboxStatus.UNSELECTED,
                andesCheckboxType = AndesCheckboxType.IDLE,
                andesCheckboxText = "Andes checkbox"
        )
        val config = configFactory.create(attrs)
        assertEquals(
                config.type.type.iconColor(context, config.status),
                R.color.andes_white.toAndesColor()
        )
    }

    @Test
    fun `Checkbox, Idle, Selected, Border`() {
        attrs = AndesCheckboxAttrs(
                andesCheckboxAlign = AndesCheckboxAlign.LEFT,
                andesCheckboxStatus = AndesCheckboxStatus.SELECTED,
                andesCheckboxType = AndesCheckboxType.IDLE,
                andesCheckboxText = "Andes checkbox"
        )
        val config = configFactory.create(attrs)
        assertEquals(
                config.type.type.borderColor(context, config.status),
                R.color.andes_accent_color_500.toAndesColor()
        )
    }

    @Test
    fun `Checkbox, Idle, Selected, Background`() {
        attrs = AndesCheckboxAttrs(
                andesCheckboxAlign = AndesCheckboxAlign.LEFT,
                andesCheckboxStatus = AndesCheckboxStatus.SELECTED,
                andesCheckboxType = AndesCheckboxType.IDLE,
                andesCheckboxText = "Andes checkbox"
        )
        val config = configFactory.create(attrs)
        assertEquals(
                config.type.type.backgroundColor(context, config.status),
                R.color.andes_accent_color_500.toAndesColor()
        )
    }

    @Test
    fun `Checkbox, Idle, Selected, Icon color`() {
        attrs = AndesCheckboxAttrs(
                andesCheckboxAlign = AndesCheckboxAlign.LEFT,
                andesCheckboxStatus = AndesCheckboxStatus.SELECTED,
                andesCheckboxType = AndesCheckboxType.IDLE,
                andesCheckboxText = "Andes checkbox"
        )
        val config = configFactory.create(attrs)
        assertEquals(
                config.type.type.iconColor(context, config.status),
                R.color.andes_white.toAndesColor()
        )
    }

    @Test
    fun `Checkbox, Disabled, Unselected, Border`() {
        attrs = AndesCheckboxAttrs(
                andesCheckboxAlign = AndesCheckboxAlign.LEFT,
                andesCheckboxStatus = AndesCheckboxStatus.UNSELECTED,
                andesCheckboxType = AndesCheckboxType.DISABLED,
                andesCheckboxText = "Andes checkbox"
        )
        val config = configFactory.create(attrs)
        assertEquals(
                config.type.type.borderColor(context, config.status),
                R.color.andes_gray_100_solid.toAndesColor()
        )
    }

    @Test
    fun `Checkbox, Disabled, Unselected, Background`() {
        attrs = AndesCheckboxAttrs(
                andesCheckboxAlign = AndesCheckboxAlign.LEFT,
                andesCheckboxStatus = AndesCheckboxStatus.UNSELECTED,
                andesCheckboxType = AndesCheckboxType.DISABLED,
                andesCheckboxText = "Andes checkbox"
        )
        val config = configFactory.create(attrs)
        assertEquals(
                config.type.type.backgroundColor(context, config.status),
                R.color.andes_white.toAndesColor()
        )
    }

    @Test
    fun `Checkbox, Disabled, Unselected, Icon color`() {
        attrs = AndesCheckboxAttrs(
                andesCheckboxAlign = AndesCheckboxAlign.LEFT,
                andesCheckboxStatus = AndesCheckboxStatus.UNSELECTED,
                andesCheckboxType = AndesCheckboxType.DISABLED,
                andesCheckboxText = "Andes checkbox"
        )
        val config = configFactory.create(attrs)
        assertEquals(
                config.type.type.iconColor(context, config.status),
                R.color.andes_gray_250_solid.toAndesColor()
        )
    }

    @Test
    fun `Checkbox, Disabled, Selected, Border`() {
        attrs = AndesCheckboxAttrs(
                andesCheckboxAlign = AndesCheckboxAlign.LEFT,
                andesCheckboxStatus = AndesCheckboxStatus.SELECTED,
                andesCheckboxType = AndesCheckboxType.DISABLED,
                andesCheckboxText = "Andes checkbox"
        )
        val config = configFactory.create(attrs)
        assertEquals(
                config.type.type.borderColor(context, config.status),
                R.color.andes_gray_100_solid.toAndesColor()
        )
    }

    @Test
    fun `Checkbox, Disabled, Selected, Background`() {
        attrs = AndesCheckboxAttrs(
                andesCheckboxAlign = AndesCheckboxAlign.LEFT,
                andesCheckboxStatus = AndesCheckboxStatus.SELECTED,
                andesCheckboxType = AndesCheckboxType.DISABLED,
                andesCheckboxText = "Andes checkbox"
        )
        val config = configFactory.create(attrs)
        assertEquals(
                config.type.type.backgroundColor(context, config.status),
                R.color.andes_gray_100_solid.toAndesColor()
        )
    }

    @Test
    fun `Checkbox, Disabled, Selected, Icon color`() {
        attrs = AndesCheckboxAttrs(
                andesCheckboxAlign = AndesCheckboxAlign.LEFT,
                andesCheckboxStatus = AndesCheckboxStatus.SELECTED,
                andesCheckboxType = AndesCheckboxType.DISABLED,
                andesCheckboxText = "Andes checkbox"
        )
        val config = configFactory.create(attrs)
        assertEquals(
                config.type.type.iconColor(context, config.status),
                R.color.andes_gray_250_solid.toAndesColor()
        )
    }

    @Test
    fun `Checkbox, Error, Unselected, Border`() {
        attrs = AndesCheckboxAttrs(
                andesCheckboxAlign = AndesCheckboxAlign.LEFT,
                andesCheckboxStatus = AndesCheckboxStatus.UNSELECTED,
                andesCheckboxType = AndesCheckboxType.ERROR,
                andesCheckboxText = "Andes checkbox"
        )
        val config = configFactory.create(attrs)
        assertEquals(
                config.type.type.borderColor(context, config.status),
                R.color.andes_red_500.toAndesColor()
        )
    }

    @Test
    fun `Checkbox, Error, Unselected, Background`() {
        attrs = AndesCheckboxAttrs(
                andesCheckboxAlign = AndesCheckboxAlign.LEFT,
                andesCheckboxStatus = AndesCheckboxStatus.UNSELECTED,
                andesCheckboxType = AndesCheckboxType.ERROR,
                andesCheckboxText = "Andes checkbox"
        )
        val config = configFactory.create(attrs)
        assertEquals(
                config.type.type.backgroundColor(context, config.status),
                R.color.andes_white.toAndesColor()
        )
    }

    @Test
    fun `Checkbox, Error, Unselected, Icon color`() {
        attrs = AndesCheckboxAttrs(
                andesCheckboxAlign = AndesCheckboxAlign.LEFT,
                andesCheckboxStatus = AndesCheckboxStatus.UNSELECTED,
                andesCheckboxType = AndesCheckboxType.ERROR,
                andesCheckboxText = "Andes checkbox"
        )
        val config = configFactory.create(attrs)
        assertEquals(
                config.type.type.iconColor(context, config.status),
                R.color.andes_white.toAndesColor()
        )
    }

    @Test
    fun `Checkbox, Error, Selected, Border`() {
        attrs = AndesCheckboxAttrs(
                andesCheckboxAlign = AndesCheckboxAlign.LEFT,
                andesCheckboxStatus = AndesCheckboxStatus.SELECTED,
                andesCheckboxType = AndesCheckboxType.ERROR,
                andesCheckboxText = "Andes checkbox"
        )
        val config = configFactory.create(attrs)
        assertEquals(
                config.type.type.borderColor(context, config.status),
                R.color.andes_red_500.toAndesColor()
        )
    }

    @Test
    fun `Checkbox, Error, Selected, Background`() {
        attrs = AndesCheckboxAttrs(
                andesCheckboxAlign = AndesCheckboxAlign.LEFT,
                andesCheckboxStatus = AndesCheckboxStatus.SELECTED,
                andesCheckboxType = AndesCheckboxType.ERROR,
                andesCheckboxText = "Andes checkbox"
        )
        val config = configFactory.create(attrs)
        assertEquals(
                config.type.type.backgroundColor(context, config.status),
                R.color.andes_white.toAndesColor()
        )
    }

    @Test
    fun `Checkbox, Error, Selected, Icon color`() {
        attrs = AndesCheckboxAttrs(
                andesCheckboxAlign = AndesCheckboxAlign.LEFT,
                andesCheckboxStatus = AndesCheckboxStatus.SELECTED,
                andesCheckboxType = AndesCheckboxType.ERROR,
                andesCheckboxText = "Andes checkbox"
        )
        val config = configFactory.create(attrs)
        assertEquals(
                config.type.type.iconColor(context, config.status),
                R.color.andes_white.toAndesColor()
        )
    }

    @Test
    fun `testing text color with type idle`() {
        attrs = AndesCheckboxAttrs(
            andesCheckboxAlign = AndesCheckboxAlign.LEFT,
            andesCheckboxStatus = AndesCheckboxStatus.SELECTED,
            andesCheckboxType = AndesCheckboxType.IDLE,
            andesCheckboxText = null
        )
        val config = configFactory.create(attrs)
        assertEquals(AndesTextViewColor.Primary, config.type.type.textColor())
    }

    @Test
    fun `testing text color with type disabled`() {
        attrs = AndesCheckboxAttrs(
            andesCheckboxAlign = AndesCheckboxAlign.LEFT,
            andesCheckboxStatus = AndesCheckboxStatus.SELECTED,
            andesCheckboxType = AndesCheckboxType.DISABLED,
            andesCheckboxText = null
        )
        val config = configFactory.create(attrs)
        assertEquals(AndesTextViewColor.Disabled, config.type.type.textColor())
    }

    @Test
    fun `testing text color with type error`() {
        attrs = AndesCheckboxAttrs(
            andesCheckboxAlign = AndesCheckboxAlign.LEFT,
            andesCheckboxStatus = AndesCheckboxStatus.SELECTED,
            andesCheckboxType = AndesCheckboxType.ERROR,
            andesCheckboxText = null
        )
        val config = configFactory.create(attrs)
        assertEquals(AndesTextViewColor.Primary, config.type.type.textColor())
    }

    @Test
    fun `Checkbox default title number of lines`() {
        andesCheckbox = AndesCheckbox(
                        context,
                        "Andes checkbox",
                        AndesCheckboxAlign.LEFT,
                        AndesCheckboxStatus.SELECTED,
                        AndesCheckboxType.IDLE)

        assertEquals(andesCheckbox.titleNumberOfLines, 1)
    }

    @Test
    fun `Checkbox title number of lines after set`() {
        andesCheckbox = AndesCheckbox(
                        context,
                        "Andes checkbox",
                        AndesCheckboxAlign.LEFT,
                        AndesCheckboxStatus.SELECTED,
                        AndesCheckboxType.IDLE)
        andesCheckbox.titleNumberOfLines = 3

        assertEquals(andesCheckbox.titleNumberOfLines, 3)
    }

    @Test
    fun `sending motion event click with a11y off works correctly`() {
        andesCheckbox = spy(
            AndesCheckbox(
            context,
            "Andes checkbox",
            AndesCheckboxAlign.LEFT,
            AndesCheckboxStatus.SELECTED,
            AndesCheckboxType.IDLE
            )
        )

        val clickMotionEvent = MotionEventBuilder
            .newBuilder()
            .setAction(MotionEvent.ACTION_DOWN)
            .build()

        val isEventIntercepted = andesCheckbox.onInterceptTouchEvent(clickMotionEvent)

        assertFalse(isEventIntercepted)
        verify(andesCheckbox, never()).onTouchEvent(clickMotionEvent)
    }

    @Test
    fun `sending motion event click with a11y on works correctly`() {
        activateTalkbackForTest(context)
        andesCheckbox = AndesCheckbox(
            context,
            "Andes checkbox",
            AndesCheckboxAlign.LEFT,
            AndesCheckboxStatus.SELECTED,
            AndesCheckboxType.IDLE
        )

        val clickMotionEvent = MotionEventBuilder
            .newBuilder()
            .setAction(MotionEvent.ACTION_DOWN)
            .build()

        val isEventIntercepted = andesCheckbox.onInterceptTouchEvent(clickMotionEvent)
        val isTouchEventConsumed = andesCheckbox.onTouchEvent(clickMotionEvent)
        assertTrue(isEventIntercepted)
        assertTrue(isTouchEventConsumed)
    }

    @Test
    fun `call on click over text component with link does not change status`() {
        andesCheckbox = AndesCheckbox(
            context,
            "Andes checkbox",
            AndesCheckboxAlign.LEFT,
            AndesCheckboxStatus.SELECTED,
            AndesCheckboxType.IDLE
        )

        andesCheckbox.bodyLinks = getBodyLinksForTest()

        andesCheckbox.checkboxText().text.getClickableSpans().forEach {
            it.onClick(andesCheckbox.checkboxText())
        }

        andesCheckbox.status assertEquals AndesCheckboxStatus.SELECTED
    }

    @Test
    fun `call on click over text component without link changes status`() {
        andesCheckbox = AndesCheckbox(
            context,
            "Andes checkbox",
            AndesCheckboxAlign.LEFT,
            AndesCheckboxStatus.SELECTED,
            AndesCheckboxType.IDLE
        )

        andesCheckbox.checkboxText().performClick()

        andesCheckbox.status assertEquals AndesCheckboxStatus.UNSELECTED
    }

    private fun getBodyLinksForTest(): AndesBodyLinks {
        return AndesBodyLinks(
            links = listOf(
                AndesBodyLink(4, 6)
            ),
            listener = { }
        )
    }

    private fun AndesCheckbox.checkboxText() = this.findViewById<AndesTextView>(R.id.checkboxText)
}
