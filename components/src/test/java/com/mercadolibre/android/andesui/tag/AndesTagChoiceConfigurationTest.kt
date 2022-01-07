package com.mercadolibre.android.andesui.tag

import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.color.toAndesColor
import com.mercadolibre.android.andesui.tag.choice.state.AndesTagChoiceState
import com.mercadolibre.android.andesui.tag.choice.mode.AndesTagChoiceMode
import com.mercadolibre.android.andesui.tag.factory.AndesChoiceTagConfigurationFactory
import com.mercadolibre.android.andesui.tag.factory.AndesTagChoiceAttrs
import com.mercadolibre.android.andesui.tag.size.AndesTagSize
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.robolectric.RuntimeEnvironment

class AndesTagChoiceConfigurationTest {
    private var context = RuntimeEnvironment.application
    private val configFactory = Mockito.spy(AndesChoiceTagConfigurationFactory)
    private lateinit var attrs: AndesTagChoiceAttrs

    @Test
    fun `Simple, Large, Simple Selected background color`() {
        attrs = AndesTagChoiceAttrs("Body", AndesTagChoiceMode.SIMPLE, AndesTagSize.LARGE,
                AndesTagChoiceState.SELECTED)
        val config = configFactory.create(attrs)
        Assert.assertEquals(config.backgroundColor, R.color.andes_accent_color_100.toAndesColor())
    }

    @Test
    fun `Simple, Large, Simple Idle background color`() {
        attrs = AndesTagChoiceAttrs("Body", AndesTagChoiceMode.SIMPLE, AndesTagSize.LARGE,
                AndesTagChoiceState.IDLE)
        val config = configFactory.create(attrs)
        Assert.assertEquals(config.backgroundColor, R.color.andes_transparent.toAndesColor())
    }

    @Test
    fun `Simple, Large, Dropdown Selected background color`() {
        attrs = AndesTagChoiceAttrs("Body", AndesTagChoiceMode.DROPDOWN, AndesTagSize.LARGE,
                AndesTagChoiceState.SELECTED)
        val config = configFactory.create(attrs)
        Assert.assertEquals(config.backgroundColor, R.color.andes_accent_color_100.toAndesColor())
    }

    @Test
    fun `Simple, Large, Dropdown Idle background color`() {
        attrs = AndesTagChoiceAttrs("Body", AndesTagChoiceMode.DROPDOWN, AndesTagSize.LARGE,
                AndesTagChoiceState.IDLE)
        val config = configFactory.create(attrs)
        Assert.assertEquals(config.backgroundColor, R.color.andes_transparent.toAndesColor())
    }

    @Test
    fun `Simple, Large, Simple Selected border color`() {
        attrs = AndesTagChoiceAttrs("Body", AndesTagChoiceMode.SIMPLE, AndesTagSize.LARGE,
                AndesTagChoiceState.SELECTED)
        val config = configFactory.create(attrs)
        Assert.assertEquals(config.borderColor, R.color.andes_accent_color_600.toAndesColor())
    }

    @Test
    fun `Simple, Large, Simple Idle border color`() {
        attrs = AndesTagChoiceAttrs("Body", AndesTagChoiceMode.SIMPLE, AndesTagSize.LARGE,
                AndesTagChoiceState.IDLE)
        val config = configFactory.create(attrs)
        Assert.assertEquals(config.borderColor, R.color.andes_gray_250.toAndesColor())
    }

    @Test
    fun `Simple, Large, Dropdown Selected border color`() {
        attrs = AndesTagChoiceAttrs("Body", AndesTagChoiceMode.DROPDOWN, AndesTagSize.LARGE,
                AndesTagChoiceState.SELECTED)
        val config = configFactory.create(attrs)
        Assert.assertEquals(config.borderColor, R.color.andes_accent_color_600.toAndesColor())
    }

    @Test
    fun `Simple, Large, Dropdown Idle border color`() {
        attrs = AndesTagChoiceAttrs("Body", AndesTagChoiceMode.DROPDOWN, AndesTagSize.LARGE,
                AndesTagChoiceState.IDLE)
        val config = configFactory.create(attrs)
        Assert.assertEquals(config.borderColor, R.color.andes_gray_250.toAndesColor())
    }

    @Test
    fun `Simple, Large, Simple Selected text color`() {
        attrs = AndesTagChoiceAttrs("Body", AndesTagChoiceMode.SIMPLE, AndesTagSize.LARGE,
                AndesTagChoiceState.SELECTED)
        val config = configFactory.create(attrs)
        Assert.assertEquals(config.textColor, R.color.andes_accent_color_600.toAndesColor())
    }

    @Test
    fun `Simple, Large, Simple Idle text color`() {
        attrs = AndesTagChoiceAttrs("Body", AndesTagChoiceMode.SIMPLE, AndesTagSize.LARGE,
                AndesTagChoiceState.IDLE)
        val config = configFactory.create(attrs)
        Assert.assertEquals(config.textColor, R.color.andes_text_color_primary.toAndesColor())
    }

    @Test
    fun `Simple, Large, Dropdown Selected text color`() {
        attrs = AndesTagChoiceAttrs("Body", AndesTagChoiceMode.DROPDOWN, AndesTagSize.LARGE,
                AndesTagChoiceState.SELECTED)
        val config = configFactory.create(attrs)
        Assert.assertEquals(config.textColor, R.color.andes_accent_color_600.toAndesColor())
    }

    @Test
    fun `Simple, Large, Dropdown Idle text color`() {
        attrs = AndesTagChoiceAttrs("Body", AndesTagChoiceMode.DROPDOWN, AndesTagSize.LARGE,
                AndesTagChoiceState.IDLE)
        val config = configFactory.create(attrs)
        Assert.assertEquals(config.textColor, R.color.andes_text_color_primary.toAndesColor())
    }

    @Test
    fun `Simple, Large, Simple Selected right content color`() {
        attrs = AndesTagChoiceAttrs("Body", AndesTagChoiceMode.SIMPLE, AndesTagSize.LARGE,
                AndesTagChoiceState.SELECTED)
        val config = configFactory.create(attrs)
        Assert.assertEquals(config.rightContentColor, R.color.andes_accent_color_600.toAndesColor())
    }

    @Test
    fun `Simple, Large, Simple Idle right content color`() {
        attrs = AndesTagChoiceAttrs("Body", AndesTagChoiceMode.SIMPLE, AndesTagSize.LARGE,
                AndesTagChoiceState.IDLE)
        val config = configFactory.create(attrs)
        Assert.assertEquals(config.rightContentColor, R.color.andes_gray_550_solid.toAndesColor())
    }

    @Test
    fun `Simple, Large, Dropdown Selected right content color`() {
        attrs = AndesTagChoiceAttrs("Body", AndesTagChoiceMode.DROPDOWN, AndesTagSize.LARGE,
                AndesTagChoiceState.SELECTED)
        val config = configFactory.create(attrs)
        Assert.assertEquals(config.rightContentColor, R.color.andes_accent_color_600.toAndesColor())
    }

    @Test
    fun `Simple, Large, Dropdown Idle right content color`() {
        attrs = AndesTagChoiceAttrs("Body", AndesTagChoiceMode.DROPDOWN, AndesTagSize.LARGE,
                AndesTagChoiceState.IDLE)
        val config = configFactory.create(attrs)
        Assert.assertEquals(config.rightContentColor, R.color.andes_gray_550_solid.toAndesColor())
    }

    @Test
    fun `Simple, dropdown idle title text`() {
        attrs = AndesTagChoiceAttrs("Body", AndesTagChoiceMode.DROPDOWN, AndesTagSize.LARGE,
                AndesTagChoiceState.IDLE)
        val config = configFactory.create(attrs)
        Assert.assertEquals(config.text, "Body")
    }

    @Test
    fun `Simple, dropdown selected title text`() {
        attrs = AndesTagChoiceAttrs("Body", AndesTagChoiceMode.DROPDOWN, AndesTagSize.LARGE,
                AndesTagChoiceState.SELECTED)
        val config = configFactory.create(attrs)
        Assert.assertEquals(config.text, "Body")
    }

    @Test
    fun `Simple, simple idle title text`() {
        attrs = AndesTagChoiceAttrs("Body", AndesTagChoiceMode.SIMPLE, AndesTagSize.LARGE,
                AndesTagChoiceState.IDLE)
        val config = configFactory.create(attrs)
        Assert.assertEquals(config.text, "Body")
    }

    @Test
    fun `Simple, simple selected title text`() {
        attrs = AndesTagChoiceAttrs("Body", AndesTagChoiceMode.SIMPLE, AndesTagSize.LARGE,
                AndesTagChoiceState.SELECTED)
        val config = configFactory.create(attrs)
        Assert.assertEquals(config.text, "Body")
    }
}
