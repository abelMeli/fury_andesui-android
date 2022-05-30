package com.mercadolibre.android.andesui.badge

import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.badge.border.AndesBadgePillBorder
import com.mercadolibre.android.andesui.badge.factory.AndesBadgePillAttrs
import com.mercadolibre.android.andesui.badge.factory.AndesBadgePillConfigurationFactory
import com.mercadolibre.android.andesui.badge.hierarchy.AndesBadgePillHierarchy
import com.mercadolibre.android.andesui.badge.size.AndesBadgePillSize
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
class AndesBadgeConfigurationQuietTest {
    private var context = RuntimeEnvironment.application

    private val configFactory = spy(AndesBadgePillConfigurationFactory)
    private lateinit var attrs: AndesBadgePillAttrs

    @Test
    fun `Pill, Quiet, Neutral, Small background color`() {
        attrs = AndesBadgePillAttrs(AndesBadgePillHierarchy.QUIET, AndesBadgeType.NEUTRAL,
                AndesBadgePillBorder.STANDARD, AndesBadgePillSize.SMALL, "Title")
        val config = configFactory.create(context, attrs)
        assertEquals(R.color.andes_gray_070_solid.toAndesColor(), config.backgroundColor)
        assertEquals(SMALL_HEIGHT, config.height)
    }

    @Test
    fun `Pill, Quiet, Highlight, Small background color`() {
        attrs = AndesBadgePillAttrs(AndesBadgePillHierarchy.QUIET, AndesBadgeType.HIGHLIGHT,
                AndesBadgePillBorder.STANDARD, AndesBadgePillSize.SMALL, "Title")
        val config = configFactory.create(context, attrs)
        assertEquals(R.color.andes_accent_color_100.toAndesColor(), config.backgroundColor)
        assertEquals(SMALL_HEIGHT, config.height)
    }

    @Test
    fun `Pill, Quiet, Success, Small background color`() {
        attrs = AndesBadgePillAttrs(AndesBadgePillHierarchy.QUIET, AndesBadgeType.SUCCESS,
                AndesBadgePillBorder.STANDARD, AndesBadgePillSize.SMALL, "Title")
        val config = configFactory.create(context, attrs)
        assertEquals(R.color.andes_green_100.toAndesColor(), config.backgroundColor)
        assertEquals(SMALL_HEIGHT, config.height)
    }

    @Test
    fun `Pill, Quiet, Error, Small background color`() {
        attrs = AndesBadgePillAttrs(AndesBadgePillHierarchy.QUIET, AndesBadgeType.ERROR,
                AndesBadgePillBorder.STANDARD, AndesBadgePillSize.SMALL, "Title")
        val config = configFactory.create(context, attrs)
        assertEquals(R.color.andes_red_100.toAndesColor(), config.backgroundColor)
        assertEquals(SMALL_HEIGHT, config.height)
    }

    @Test
    fun `Pill, Quiet, Warning, Small background color`() {
        attrs = AndesBadgePillAttrs(AndesBadgePillHierarchy.QUIET, AndesBadgeType.WARNING,
                AndesBadgePillBorder.STANDARD, AndesBadgePillSize.SMALL, "Title")
        val config = configFactory.create(context, attrs)
        assertEquals(R.color.andes_orange_100.toAndesColor(), config.backgroundColor)
        assertEquals(SMALL_HEIGHT, config.height)
    }

    @Test
    fun `Pill, Quiet, Neutral, Large background color`() {
        attrs = AndesBadgePillAttrs(AndesBadgePillHierarchy.QUIET, AndesBadgeType.NEUTRAL,
                AndesBadgePillBorder.STANDARD, AndesBadgePillSize.LARGE, "Title")
        val config = configFactory.create(context, attrs)
        assertEquals(R.color.andes_gray_070_solid.toAndesColor(), config.backgroundColor)
        assertEquals(LARGE_HEIGHT, config.height)
    }

    @Test
    fun `Pill, Quiet, Highlight, Large background color`() {
        attrs = AndesBadgePillAttrs(AndesBadgePillHierarchy.QUIET, AndesBadgeType.HIGHLIGHT,
                AndesBadgePillBorder.STANDARD, AndesBadgePillSize.LARGE, "Title")
        val config = configFactory.create(context, attrs)
        assertEquals(R.color.andes_accent_color_100.toAndesColor(), config.backgroundColor)
        assertEquals(LARGE_HEIGHT, config.height)
    }

    @Test
    fun `Pill, Quiet, Success, Large background color`() {
        attrs = AndesBadgePillAttrs(AndesBadgePillHierarchy.QUIET, AndesBadgeType.SUCCESS,
                AndesBadgePillBorder.STANDARD, AndesBadgePillSize.LARGE, "Title")
        val config = configFactory.create(context, attrs)
        assertEquals(R.color.andes_green_100.toAndesColor(), config.backgroundColor)
        assertEquals(LARGE_HEIGHT, config.height)
    }

    @Test
    fun `Pill, Quiet, Error, Large background color`() {
        attrs = AndesBadgePillAttrs(AndesBadgePillHierarchy.QUIET, AndesBadgeType.ERROR,
                AndesBadgePillBorder.STANDARD, AndesBadgePillSize.LARGE, "Title")
        val config = configFactory.create(context, attrs)
        assertEquals(R.color.andes_red_100.toAndesColor(), config.backgroundColor)
        assertEquals(LARGE_HEIGHT, config.height)
    }

    @Test
    fun `Pill, Quiet, Warning, Large background color`() {
        attrs = AndesBadgePillAttrs(AndesBadgePillHierarchy.QUIET, AndesBadgeType.WARNING,
                AndesBadgePillBorder.STANDARD, AndesBadgePillSize.LARGE, "Title")
        val config = configFactory.create(context, attrs)
        assertEquals(R.color.andes_orange_100.toAndesColor(), config.backgroundColor)
        assertEquals(LARGE_HEIGHT, config.height)
    }

    companion object {
        private const val SMALL_HEIGHT = 16F
        private const val LARGE_HEIGHT = 24F
    }
}
