package com.mercadolibre.android.andesui.checkbox

import android.os.Build
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.checkbox.align.AndesCheckboxAlign
import com.mercadolibre.android.andesui.checkbox.factory.AndesCheckboxAttrs
import com.mercadolibre.android.andesui.checkbox.factory.AndesCheckboxConfigurationFactory
import com.mercadolibre.android.andesui.checkbox.status.AndesCheckboxStatus
import com.mercadolibre.android.andesui.checkbox.type.AndesCheckboxType
import com.mercadolibre.android.andesui.color.toAndesColor
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.spy
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
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
}
