package com.mercadolibre.android.andesui.radiobutton

import android.widget.RadioButton
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.color.toAndesColor
import com.mercadolibre.android.andesui.radiobutton.align.AndesRadioButtonAlign
import com.mercadolibre.android.andesui.radiobutton.factory.AndesRadioButtonAttrs
import com.mercadolibre.android.andesui.radiobutton.factory.AndesRadioButtonConfigurationFactory
import com.mercadolibre.android.andesui.radiobutton.status.AndesRadioButtonStatus
import com.mercadolibre.android.andesui.radiobutton.type.AndesRadioButtonType
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.spy
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
class AndesRadioButtonTest {

    private var context = RuntimeEnvironment.application
    private val configFactory = spy(AndesRadioButtonConfigurationFactory)
    private lateinit var attrs: AndesRadioButtonAttrs

    @Test
    fun `RadioButton, Idle, Unselected, Border`() {
        attrs = AndesRadioButtonAttrs(
                andesRadioButtonAlign = AndesRadioButtonAlign.LEFT,
                andesRadioButtonStatus = AndesRadioButtonStatus.UNSELECTED,
                andesRadioButtonType = AndesRadioButtonType.IDLE,
                andesRadioButtonText = "Andes checkbox"
        )
        val config = configFactory.create(attrs)
        assertEquals(
                config.type.type.borderColor(context, config.status),
                R.color.andes_gray_250_solid.toAndesColor()
        )
    }

    @Test
    fun `RadioButton, Idle, Unselected, Background`() {
        attrs = AndesRadioButtonAttrs(
                andesRadioButtonAlign = AndesRadioButtonAlign.LEFT,
                andesRadioButtonStatus = AndesRadioButtonStatus.UNSELECTED,
                andesRadioButtonType = AndesRadioButtonType.IDLE,
                andesRadioButtonText = "Andes checkbox"
        )
        val config = configFactory.create(attrs)
        assertEquals(
                config.type.type.backgroundColor(context, config.status),
                R.color.andes_white.toAndesColor()
        )
    }

    @Test
    fun `RadioButton, Idle, Unselected, Icon color`() {
        attrs = AndesRadioButtonAttrs(
                andesRadioButtonAlign = AndesRadioButtonAlign.LEFT,
                andesRadioButtonStatus = AndesRadioButtonStatus.UNSELECTED,
                andesRadioButtonType = AndesRadioButtonType.IDLE,
                andesRadioButtonText = "Andes checkbox"
        )
        val config = configFactory.create(attrs)
        assertEquals(
                config.type.type.iconColor(context, config.status),
                R.color.andes_white.toAndesColor()
        )
    }

    @Test
    fun `RadioButton, Idle, Selected, Border`() {
        attrs = AndesRadioButtonAttrs(
                andesRadioButtonAlign = AndesRadioButtonAlign.LEFT,
                andesRadioButtonStatus = AndesRadioButtonStatus.SELECTED,
                andesRadioButtonType = AndesRadioButtonType.IDLE,
                andesRadioButtonText = "Andes checkbox"
        )
        val config = configFactory.create(attrs)
        assertEquals(
                config.type.type.borderColor(context, config.status),
                R.color.andes_accent_color_500.toAndesColor()
        )
    }

    @Test
    fun `RadioButton, Idle, Selected, Background`() {
        attrs = AndesRadioButtonAttrs(
                andesRadioButtonAlign = AndesRadioButtonAlign.LEFT,
                andesRadioButtonStatus = AndesRadioButtonStatus.SELECTED,
                andesRadioButtonType = AndesRadioButtonType.IDLE,
                andesRadioButtonText = "Andes checkbox"
        )
        val config = configFactory.create(attrs)
        assertEquals(
                config.type.type.backgroundColor(context, config.status),
                R.color.andes_accent_color_500.toAndesColor()
        )
    }

    @Test
    fun `RadioButton, Idle, Selected, Icon color`() {
        attrs = AndesRadioButtonAttrs(
                andesRadioButtonAlign = AndesRadioButtonAlign.LEFT,
                andesRadioButtonStatus = AndesRadioButtonStatus.SELECTED,
                andesRadioButtonType = AndesRadioButtonType.IDLE,
                andesRadioButtonText = "Andes checkbox"
        )
        val config = configFactory.create(attrs)
        assertEquals(
                config.type.type.iconColor(context, config.status),
                R.color.andes_white.toAndesColor()
        )
    }

    @Test
    fun `RadioButton, Disabled, Unselected, Border`() {
        attrs = AndesRadioButtonAttrs(
                andesRadioButtonAlign = AndesRadioButtonAlign.LEFT,
                andesRadioButtonStatus = AndesRadioButtonStatus.UNSELECTED,
                andesRadioButtonType = AndesRadioButtonType.DISABLED,
                andesRadioButtonText = "Andes checkbox"
        )
        val config = configFactory.create(attrs)
        assertEquals(
                config.type.type.borderColor(context, config.status),
                R.color.andes_gray_100_solid.toAndesColor()
        )
    }

    @Test
    fun `RadioButton, Disabled, Unselected, Background`() {
        attrs = AndesRadioButtonAttrs(
                andesRadioButtonAlign = AndesRadioButtonAlign.LEFT,
                andesRadioButtonStatus = AndesRadioButtonStatus.UNSELECTED,
                andesRadioButtonType = AndesRadioButtonType.DISABLED,
                andesRadioButtonText = "Andes checkbox"
        )
        val config = configFactory.create(attrs)
        assertEquals(
                config.type.type.backgroundColor(context, config.status),
                R.color.andes_white.toAndesColor()
        )
    }

    @Test
    fun `RadioButton, Disabled, Unselected, Icon color`() {
        attrs = AndesRadioButtonAttrs(
                andesRadioButtonAlign = AndesRadioButtonAlign.LEFT,
                andesRadioButtonStatus = AndesRadioButtonStatus.UNSELECTED,
                andesRadioButtonType = AndesRadioButtonType.DISABLED,
                andesRadioButtonText = "Andes checkbox"
        )
        val config = configFactory.create(attrs)
        assertEquals(
                config.type.type.iconColor(context, config.status),
                R.color.andes_gray_250_solid.toAndesColor()
        )
    }

    @Test
    fun `RadioButton, Disabled, Selected, Border`() {
        attrs = AndesRadioButtonAttrs(
                andesRadioButtonAlign = AndesRadioButtonAlign.LEFT,
                andesRadioButtonStatus = AndesRadioButtonStatus.SELECTED,
                andesRadioButtonType = AndesRadioButtonType.DISABLED,
                andesRadioButtonText = "Andes checkbox"
        )
        val config = configFactory.create(attrs)
        assertEquals(
                config.type.type.borderColor(context, config.status),
                R.color.andes_gray_100_solid.toAndesColor()
        )
    }

    @Test
    fun `RadioButton, Disabled, Selected, Background`() {
        attrs = AndesRadioButtonAttrs(
                andesRadioButtonAlign = AndesRadioButtonAlign.LEFT,
                andesRadioButtonStatus = AndesRadioButtonStatus.SELECTED,
                andesRadioButtonType = AndesRadioButtonType.DISABLED,
                andesRadioButtonText = "Andes checkbox"
        )
        val config = configFactory.create(attrs)
        assertEquals(
                config.type.type.backgroundColor(context, config.status),
                R.color.andes_gray_100_solid.toAndesColor()
        )
    }

    @Test
    fun `RadioButton, Disabled, Selected, Icon color`() {
        attrs = AndesRadioButtonAttrs(
                andesRadioButtonAlign = AndesRadioButtonAlign.LEFT,
                andesRadioButtonStatus = AndesRadioButtonStatus.SELECTED,
                andesRadioButtonType = AndesRadioButtonType.DISABLED,
                andesRadioButtonText = "Andes checkbox"
        )
        val config = configFactory.create(attrs)
        assertEquals(
                config.type.type.iconColor(context, config.status),
                R.color.andes_gray_250_solid.toAndesColor()
        )
    }

    @Test
    fun `RadioButton, Error, Unselected, Border`() {
        attrs = AndesRadioButtonAttrs(
                andesRadioButtonAlign = AndesRadioButtonAlign.LEFT,
                andesRadioButtonStatus = AndesRadioButtonStatus.UNSELECTED,
                andesRadioButtonType = AndesRadioButtonType.ERROR,
                andesRadioButtonText = "Andes checkbox"
        )
        val config = configFactory.create(attrs)
        assertEquals(
                config.type.type.borderColor(context, config.status),
                R.color.andes_red_500.toAndesColor()
        )
    }

    @Test
    fun `RadioButton, Error, Unselected, Background`() {
        attrs = AndesRadioButtonAttrs(
                andesRadioButtonAlign = AndesRadioButtonAlign.LEFT,
                andesRadioButtonStatus = AndesRadioButtonStatus.UNSELECTED,
                andesRadioButtonType = AndesRadioButtonType.ERROR,
                andesRadioButtonText = "Andes checkbox"
        )
        val config = configFactory.create(attrs)
        assertEquals(
                config.type.type.backgroundColor(context, config.status),
                R.color.andes_white.toAndesColor()
        )
    }

    @Test
    fun `RadioButton, Error, Unselected, Icon color`() {
        attrs = AndesRadioButtonAttrs(
                andesRadioButtonAlign = AndesRadioButtonAlign.LEFT,
                andesRadioButtonStatus = AndesRadioButtonStatus.UNSELECTED,
                andesRadioButtonType = AndesRadioButtonType.ERROR,
                andesRadioButtonText = "Andes checkbox"
        )
        val config = configFactory.create(attrs)
        assertEquals(
                config.type.type.iconColor(context, config.status),
                R.color.andes_white.toAndesColor()
        )
    }

    @Test
    fun `RadioButton, Error, Selected, Border`() {
        attrs = AndesRadioButtonAttrs(
                andesRadioButtonAlign = AndesRadioButtonAlign.LEFT,
                andesRadioButtonStatus = AndesRadioButtonStatus.SELECTED,
                andesRadioButtonType = AndesRadioButtonType.ERROR,
                andesRadioButtonText = "Andes checkbox"
        )
        val config = configFactory.create(attrs)
        assertEquals(
                config.type.type.borderColor(context, config.status),
                R.color.andes_red_500.toAndesColor()
        )
    }

    @Test
    fun `RadioButton, Error, Selected, Background`() {
        attrs = AndesRadioButtonAttrs(
                andesRadioButtonAlign = AndesRadioButtonAlign.LEFT,
                andesRadioButtonStatus = AndesRadioButtonStatus.SELECTED,
                andesRadioButtonType = AndesRadioButtonType.ERROR,
                andesRadioButtonText = "Andes checkbox"
        )
        val config = configFactory.create(attrs)
        assertEquals(
                config.type.type.backgroundColor(context, config.status),
                R.color.andes_white.toAndesColor()
        )
    }

    @Test
    fun `RadioButton, Error, Selected, Icon color`() {
        attrs = AndesRadioButtonAttrs(
                andesRadioButtonAlign = AndesRadioButtonAlign.LEFT,
                andesRadioButtonStatus = AndesRadioButtonStatus.SELECTED,
                andesRadioButtonType = AndesRadioButtonType.ERROR,
                andesRadioButtonText = "Andes checkbox"
        )
        val config = configFactory.create(attrs)
        assertEquals(
                config.type.type.iconColor(context, config.status),
                R.color.andes_white.toAndesColor()
        )
    }

    @Test
    fun `accessibility class name is correct`() {
        val nativeRadioButtonA11yClassName = RadioButton::class.java.name
        val andesRadioButton = AndesRadioButton(context, "text")

        val andesRadioButtonA11yClassName = andesRadioButton.accessibilityClassName

        assertEquals(nativeRadioButtonA11yClassName, andesRadioButtonA11yClassName)
    }
}
