package com.mercadolibre.android.andesui.badge

import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.badge.factory.AndesBadgeDotAttrs
import com.mercadolibre.android.andesui.badge.factory.AndesBadgeDotConfigurationFactory
import com.mercadolibre.android.andesui.badge.type.AndesBadgeType
import com.mercadolibre.android.andesui.color.toAndesColor
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
class AndesBadgeDotConfigurationTest {

    private var context = RuntimeEnvironment.application

    private val configFactory = spy(AndesBadgeDotConfigurationFactory)
    private lateinit var attrs: AndesBadgeDotAttrs

    @Test
    fun `Dot, Neutral`() {
        attrs = AndesBadgeDotAttrs(AndesBadgeType.NEUTRAL)
        val config = configFactory.create(context, attrs)
        assertEquals(R.color.andes_gray_550_solid.toAndesColor(), config.backgroundColor)
    }

    @Test
    fun `Dot, Highlight`() {
        attrs = AndesBadgeDotAttrs(AndesBadgeType.HIGHLIGHT)
        val config = configFactory.create(context, attrs)
        assertEquals(R.color.andes_accent_color_500.toAndesColor(), config.backgroundColor)
    }

    @Test
    fun `Dot, Success`() {
        attrs = AndesBadgeDotAttrs(AndesBadgeType.SUCCESS)
        val config = configFactory.create(context, attrs)
        assertEquals(R.color.andes_green_500.toAndesColor(), config.backgroundColor)
    }

    @Test
    fun `Dot, Error`() {
        attrs = AndesBadgeDotAttrs(AndesBadgeType.ERROR)
        val config = configFactory.create(context, attrs)
        assertEquals(R.color.andes_red_500.toAndesColor(), config.backgroundColor)
    }

    @Test
    fun `Dot, Warning`() {
        attrs = AndesBadgeDotAttrs(AndesBadgeType.WARNING)
        val config = configFactory.create(context, attrs)
        assertEquals(R.color.andes_orange_500.toAndesColor(), config.backgroundColor)
    }
}
